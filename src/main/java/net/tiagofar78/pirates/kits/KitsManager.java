package net.tiagofar78.pirates.kits;

import net.tiagofar78.pirates.Pirates;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KitsManager {

    // Player related

    private static final Map<String, KitFactory> selectedKits = new HashMap<>();

    public static final KitFactory getPlayerKit(String playerName) {
        return selectedKits.get(playerName);
    }

    public static void assignKitToPlayer(String playerName, KitFactory factory) {
        selectedKits.put(playerName, factory);
    }

    // Kits loading related

    private static final List<KitFactory> availableKits = loadKits();

    public static List<KitFactory> getAvailableKits() {
        return availableKits;
    }

    private static YamlConfiguration getYamlConfiguration() {
        File configFile = new File(Pirates.getInstance().getDataFolder(), "config.yml");
        return YamlConfiguration.loadConfiguration(configFile);
    }

    private static List<KitFactory> loadKits() {
        YamlConfiguration config = getYamlConfiguration();
        // load command for each
        // return a list with them
        return null;
    }

}
