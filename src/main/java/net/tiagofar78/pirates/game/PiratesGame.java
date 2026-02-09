package net.tiagofar78.pirates.game;

import io.github.tiagofar78.grindstone.bukkit.BukkitPlayer;
import io.github.tiagofar78.grindstone.bukkit.Scheduler;
import io.github.tiagofar78.grindstone.game.Minigame;
import io.github.tiagofar78.grindstone.game.MinigameMap;
import io.github.tiagofar78.grindstone.game.MinigamePlayer;
import io.github.tiagofar78.grindstone.game.MinigameSettings;
import io.github.tiagofar78.grindstone.game.MinigameTeam;
import io.github.tiagofar78.grindstone.game.TeamPreset;
import io.github.tiagofar78.grindstone.game.phases.Phase;

import net.tiagofar78.pirates.PiratesConfig;
import net.tiagofar78.pirates.maps.PiratesMap;

import org.bukkit.Location;

import java.util.Collection;
import java.util.List;

public class PiratesGame extends Minigame {

    public PiratesGame(MinigameMap map, MinigameSettings settings, List<Collection<String>> parties) {
        super(map, settings, parties);
    }

//  #########################################
//  #                 Lobby                 #
//  #########################################

    @Override
    public void load() {
        PiratesMap map = (PiratesMap) getMap();
        map.load();
        for (int i = 0; i < getTeams().size(); i++) {
            PiratesTeam<? extends MinigamePlayer> team = (PiratesTeam<? extends MinigamePlayer>) getTeams().get(i);
            team.setSpawnPointsRemaining(map.getActiveSpawnPoints(i).size());
        }
    }

    @Override
    public Phase newOngoingPhase() {
        return new OngoingPhase(this);
    }

    @Override
    public void disable() {
        // TODO
    }

    @Override
    public MinigamePlayer createPlayer(String name) {
        return new PiratesPlayer(name);
    }

    @Override
    public MinigameTeam<? extends MinigamePlayer> createTeam(List<MinigamePlayer> party) {
        TeamPreset teamPreset = TeamPreset.values()[getTeams().size()];
        PiratesTeam<PiratesPlayer> team = new PiratesTeam<>(teamPreset);
        for (MinigamePlayer player : party) {
            team.addMember((PiratesPlayer) player);
        }

        return team;
    }

//  ########################################
//  #              Game Logic              #
//  ########################################

    private Location getRespawnLocation(MinigamePlayer player) {
        int teamIndex = getTeamIndex(player);
        PiratesTeam<? extends MinigamePlayer> team = (PiratesTeam<? extends MinigamePlayer>) getTeams().get(teamIndex);
        if (team.getSpawnPointsRemaining() == 0) {
            return null;
        }

        return ((PiratesMap) getMap()).getRandomActiveSpawnPoint(teamIndex);
    }

    @Override
    public void addPlayerToGame(MinigamePlayer minigamePlayer) {
        PiratesPlayer player = (PiratesPlayer) minigamePlayer;
        if (!player.isAlive()) {
            makeSpectator(player);
            return;
        }

        Location spawnPointLocation = getRespawnLocation(player);
        if (spawnPointLocation == null) {
            return;
        }

        spawn(player, spawnPointLocation);
    }

    private void respawn(PiratesPlayer player) {
        makeSpectator(player);
        Location spawnPointLocation = getRespawnLocation(player);
        if (spawnPointLocation == null) {
            return;
        }

        executeRespawnCountdown(player, spawnPointLocation);
    }

    protected void spawn(PiratesPlayer player, Location spawnPointLoc) {
        player.setSpectator(false);
        player.spawn();
        BukkitPlayer.teleport(player, spawnPointLoc.clone().add(0, 1, 0));
    }

    public void kill(String killerName, String killedName) {
        kill((PiratesPlayer) getPlayer(killerName), (PiratesPlayer) getPlayer(killedName));
    }

    private void kill(PiratesPlayer killer, PiratesPlayer killed) {
        killer.killed();
        killed.died();

        // TODO Send kill message
        respawn(killed);
    }

    private void executeRespawnCountdown(PiratesPlayer player, Location spawnPoint) {
        executeRespawnCountdown(player, spawnPoint, PiratesConfig.getInstance().secondsToRespawn);
    }

    private void executeRespawnCountdown(PiratesPlayer player, Location spawnPoint, int secondsRemaining) {
        if (secondsRemaining == 0) {
            spawn(player, spawnPoint);
            return;
        }

        // TODO send respawn time

        Scheduler.runLater(1, new Runnable() {

            @Override
            public void run() {
                executeRespawnCountdown(player, spawnPoint, secondsRemaining - 1);
            }
        });
    }

    @Override
    public void removePlayerFromGame(MinigamePlayer minigamePlayer) {
        PiratesPlayer player = (PiratesPlayer) minigamePlayer;
        player.died();
        respawn(player);
    }

    public void breakSpawnPoint(String playerName, Location location) {
        PiratesMap map = (PiratesMap) getMap();
        MinigamePlayer player = getPlayer(playerName);
        int teamIndex = getTeamIndex(player);
        if (map.getActiveSpawnPoints(teamIndex).contains(location)) {
            // TODO Send cant break own spawnpoint message
            return;
        }

        map.destroySpawnPoint(location);

        int indexOfTeamWithSpawnPoint = map.indexOfTeamWithSpawnPoint(location);
        MinigameTeam<? extends MinigamePlayer> teamWithBrokenSpawnPoint = getTeams().get(indexOfTeamWithSpawnPoint);
        // TODO Broadcast break

        if (map.getActiveSpawnPoints(indexOfTeamWithSpawnPoint).size() == 0) {
            // TODO warn no spawnpoints
        }
    }

    @Override
    public void resolvePlayerOutcomes() {
        // TODO statistics
    }

    @Override
    public void sendGameExplanationMessage() {
        // TODO
    }

}
