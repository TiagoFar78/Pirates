package net.tiagofar78.pirates;

import net.tiagofar78.pirates.listener.GameRestrictionsListener;
import net.tiagofar78.pirates.listener.PlayerListener;

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

        getServer().getPluginManager().registerEvents(new PlayerListener(), this);
        getServer().getPluginManager().registerEvents(new GameRestrictionsListener(), this);
    }

}
