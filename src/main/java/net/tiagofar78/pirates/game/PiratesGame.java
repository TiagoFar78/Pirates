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
        // TODO
    }

//  #########################################
//  #                 Lobby                 #
//  #########################################

    @Override
    public void load() {
        getMap().load();
        for (int i = 0; i < getTeams().size(); i++) {
            PiratesTeam<? extends MinigamePlayer> team = (PiratesTeam<? extends MinigamePlayer>) getTeams().get(i);
            team.setSpawnPointsRemaining(((PiratesMap) getMap()).getActiveSpawnPoints(i).size());
        }
    }

    @Override
    public Phase newOngoingPhase() {
        // TODO
        return null;
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

    private void spawn(PiratesPlayer player, Location loc) {
        player.setSpectator(false);
        player.spawn();
        BukkitPlayer.teleport(player, loc.clone().add(0, 1, 0));
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

    @Override
    public void resolvePlayerOutcomes() {
        // TODO statistics
    }

    @Override
    public void sendGameExplanationMessage() {
        // TODO
    }

}
