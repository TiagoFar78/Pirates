package net.tiagofar78.pirates.game;

import io.github.tiagofar78.grindstone.game.Minigame;
import io.github.tiagofar78.grindstone.game.MinigameMap;
import io.github.tiagofar78.grindstone.game.MinigamePlayer;
import io.github.tiagofar78.grindstone.game.MinigameSettings;
import io.github.tiagofar78.grindstone.game.MinigameTeam;
import io.github.tiagofar78.grindstone.game.TeamPreset;
import io.github.tiagofar78.grindstone.game.phases.Phase;

import java.util.Collection;
import java.util.List;

public class PiratesGame extends Minigame {

    public PiratesGame(MinigameMap map, MinigameSettings settings, List<Collection<String>> parties) {
        super(map, settings, parties);
        // TODO
    }

    @Override
    public void addPlayerToGame(MinigamePlayer player) {
        // TODO
    }

    @Override
    public void removePlayerFromGame(MinigamePlayer player) {
        // TODO
    }

    @Override
    public void load() {
        // TODO
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

    @Override
    public void resolvePlayerOutcomes() {
        // TODO
    }

    @Override
    public void teleportToPreparingRoom() {
        // TODO
    }

    @Override
    public void sendGameExplanationMessage() {
        // TODO
    }

}
