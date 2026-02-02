package net.tiagofar78.pirates;

import net.tiagofar78.pirates.listener.PlayerListener;
import net.tiagofar78.pirates.listener.WorldListener;

import org.bukkit.plugin.java.JavaPlugin;

public class Pirates extends JavaPlugin {

    @Override
    public void onEnable() {
        // TODO Register gamemodes

        getServer().getPluginManager().registerEvents(new PlayerListener(), this);
        getServer().getPluginManager().registerEvents(new WorldListener(), this);
    }

}
