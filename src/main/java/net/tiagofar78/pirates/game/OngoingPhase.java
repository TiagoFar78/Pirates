package net.tiagofar78.pirates.game;

import io.github.tiagofar78.grindstone.game.Minigame;
import io.github.tiagofar78.grindstone.game.MinigamePlayer;
import io.github.tiagofar78.grindstone.game.phases.FinishedPhase;
import io.github.tiagofar78.grindstone.game.phases.Phase;

import net.tiagofar78.pirates.maps.PiratesMap;

import org.bukkit.Location;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OngoingPhase extends Phase {

    public OngoingPhase(Minigame game) {
        super(game);
    }

    @Override
    public Phase next() {
        return new FinishedPhase(getGame());
    }

    @Override
    public boolean isClockStopped() {
        return false;
    }

    @Override
    public boolean hasGameStarted() {
        return true;
    }

    @Override
    public boolean hasGameEnded() {
        return false;
    }

    @Override
    public boolean isGameDisabled() {
        return false;
    }

    @Override
    public void start() {
        PiratesGame game = (PiratesGame) getGame();
        PiratesMap map = (PiratesMap) game.getMap();
        for (int i = 0; i < game.getTeams().size(); i++) {
            List<? extends MinigamePlayer> members = game.getTeams().get(i).getMembers();
            List<Location> spawnPoints = map.getActiveSpawnPoints(i);
            int[] distribution = playersDistributionToSpawnPoints(members.size(), spawnPoints.size());
            for (int j = 0; j < members.size(); j++) {
                PiratesPlayer member = (PiratesPlayer) members.get(j);
                game.spawn(member, spawnPoints.get(distribution[j]));
            }
        }
    }

    public int[] playersDistributionToSpawnPoints(int playerCount, int spawnPointCount) {
        List<Integer> players = new ArrayList<>(playerCount);
        for (int i = 0; i < playerCount; i++) {
            players.add(i);
        }

        Collections.shuffle(players);

        int baseSize = playerCount / spawnPointCount;
        int remainder = playerCount % spawnPointCount;

        int[] memberToRoom = new int[playerCount];

        int index = 0;
        for (int i = 0; i < spawnPointCount; i++) {
            int roomSize = baseSize + (i < remainder ? 1 : 0);
            for (int j = 0; j < roomSize; j++) {
                memberToRoom[players.get(index)] = i;
                index++;
            }
        }

        return memberToRoom;
    }

}
