package net.tiagofar78.pirates.maps;

import io.github.tiagofar78.grindstone.game.MinigameMap;

import org.bukkit.Location;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class PiratesMap extends MinigameMap {

    private Location preparingRoom;
    private List<List<SpawnPoint>> teamsSpawnPoints;

    public PiratesMap(String name, Location preparingRoom, List<List<Location>> teamsSpawnPoints) {
        super(name);
        this.preparingRoom = preparingRoom;
        this.teamsSpawnPoints = new ArrayList<>();
        for (List<Location> teamSpawnPoints : teamsSpawnPoints) {
            this.teamsSpawnPoints.add(
                    teamSpawnPoints.stream().map(p -> new SpawnPoint(p)).collect(Collectors.toList())
            );
        }
    }

    public List<SpawnPoint> getActiveSpawnPoints(int teamIndex) {
        List<SpawnPoint> spawnPoints = teamsSpawnPoints.get(teamIndex);
        return spawnPoints.stream().filter(p -> !p.isBroken).toList();
    }

    public Location getRandomActiveSpawnPoint(int teamIndex) {
        List<SpawnPoint> activeSpawnPoints = getActiveSpawnPoints(teamIndex);
        if (activeSpawnPoints.isEmpty()) {
            return null;
        }

        int randomIndex = new Random().nextInt(activeSpawnPoints.size());
        return activeSpawnPoints.get(randomIndex).location;
    }

    public void destroySpawnPoint(Location location) {
        for (List<SpawnPoint> teamSpawnPoints : teamsSpawnPoints) {
            for (SpawnPoint spawnPoint : teamSpawnPoints) {
                Location loc = spawnPoint.location;
                if (loc.equals(location)) {
                    spawnPoint.isBroken = true;
                    loc.getWorld().getBlockAt(loc).breakNaturally(null);
                    return;
                }
            }
        }
    }

    @Override
    public void load() {
        for (List<SpawnPoint> teamSpawnPoint : teamsSpawnPoints) {
            placeSpawnPoints(teamSpawnPoint);
        }
    }

    private void placeSpawnPoints(List<SpawnPoint> spawnPoints) {
        for (SpawnPoint spawnPoint : spawnPoints) {
            Location loc = spawnPoint.location;
            loc.getWorld().getBlockAt(loc).setType(Material.LIME_STAINED_GLASS);
        }
    }

    @Override
    public Location preparingRoomLocation() {
        return preparingRoom;
    }

    private class SpawnPoint {

        public final Location location;
        public boolean isBroken = false;

        public SpawnPoint(Location location) {
            this.location = location;
        }

    }

}
