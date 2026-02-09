package net.tiagofar78.pirates;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class Pirates extends JavaPlugin {

    private static Pirates instance;

    public static Pirates getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        if (!new File(getDataFolder(), "config.yml").exists()) {
            saveDefaultConfig();
        }

        instance = (Pirates) Bukkit.getServer().getPluginManager().getPlugin("TF_Pirates");

        PiratesConfig.load();

        // TODO Register gamemodes

//        getServer().getPluginManager().registerEvents(new ???Listener(), this);
    }

}
