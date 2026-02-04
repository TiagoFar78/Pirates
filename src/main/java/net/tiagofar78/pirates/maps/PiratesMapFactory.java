package net.tiagofar78.pirates.maps;

import io.github.tiagofar78.grindstone.game.MapFactory;
import io.github.tiagofar78.grindstone.game.MinigameMap;

import net.tiagofar78.pirates.Pirates;
import net.tiagofar78.pirates.PiratesConfig;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PiratesMapFactory extends MapFactory {

    private static YamlConfiguration getYamlConfiguration(String mapName) {
        File mapsDir = new File(Pirates.getInstance().getDataFolder(), "maps");
        if (!mapsDir.exists()) {
            mapsDir.mkdir();
        }

        File configFile = new File(mapsDir, mapName + ".yml");
        if (!configFile.exists()) {
            Pirates.getInstance().saveResource("maps" + File.separator + mapName + ".yml", false);
        }

        return YamlConfiguration.loadConfiguration(configFile);
    }

    private int indexInWorld;

    private Location preparingRoom;
    private List<List<Location>> teamsSpawnPoints = new ArrayList<>();

    public PiratesMapFactory(String name) {
        super(name, PiratesConfig.getInstance().worldName);

        YamlConfiguration mapConfig = getYamlConfiguration(name);

        indexInWorld = mapConfig.getInt("IndexInWorld");

        World world = Bukkit.getWorld(PiratesConfig.getInstance().worldName);
        preparingRoom = new Location(
                world,
                mapConfig.getInt("PreparingRoom.x"),
                mapConfig.getInt("PreparingRoom.y"),
                mapConfig.getInt("PreparingRoom.z")
        );

        for (int i = 1; mapConfig.contains("Team" + i); i++) {
            List<Location> teamSpawnPoints = new ArrayList<>();
            for (int j = 1; mapConfig.contains("Team" + i + ".SpawnPoint" + j); j++) {
                teamSpawnPoints.add(
                        new Location(
                                world,
                                mapConfig.getInt("Team" + i + ".SpawnPoint" + j + ".x"),
                                mapConfig.getInt("Team" + i + ".SpawnPoint" + j + ".y"),
                                mapConfig.getInt("Team" + i + ".SpawnPoint" + j + ".z")
                        )
                );
            }
            teamsSpawnPoints.add(teamSpawnPoints);
        }
    }

    @Override
    public int indexInWorld() {
        return indexInWorld;
    }

    @Override
    public MinigameMap create(Location referenceBlock) {
        List<List<Location>> teamsSpawnsPoints = new ArrayList<>();
        for (List<Location> teamSpawnPoints : teamsSpawnPoints) {
            teamsSpawnsPoints.add(
                    teamSpawnPoints.stream().map(p -> p.clone().add(referenceBlock)).collect(Collectors.toList())
            );
        }

        return new PiratesMap(getName(), preparingRoom.clone().add(referenceBlock), teamsSpawnsPoints);
    }

}
