package net.tiagofar78.pirates;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class PiratesConfig {

    private static YamlConfiguration getYamlConfiguration() {
        File configFile = new File(Pirates.getInstance().getDataFolder(), "config.yml");
        return YamlConfiguration.loadConfiguration(configFile);
    }

    public static void load() {
        instance = new PiratesConfig();
    }

    private static PiratesConfig instance;

    public static PiratesConfig getInstance() {
        return instance;
    }

    public String worldName;
    public int secondsToRespawn;


    private PiratesConfig() {
        YamlConfiguration config = getYamlConfiguration();

        worldName = config.getString("WorldName");
        secondsToRespawn = config.getInt("SecondsToRespawn");
    }

}
