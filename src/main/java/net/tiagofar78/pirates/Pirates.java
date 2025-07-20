package net.tiagofar78.pirates;

import org.bukkit.plugin.java.JavaPlugin;

import net.tiagofar78.pirates.commands.PiratesCommand;
import net.tiagofar78.pirates.listener.PlayerListener;
import net.tiagofar78.pirates.listener.WorldListener;

public class Pirates extends JavaPlugin {

    private static final String COMMAND_LABEL = "Pirates";
    
    @Override
    public void onEnable() {
        getCommand(COMMAND_LABEL).setExecutor(new PiratesCommand());

        getServer().getPluginManager().registerEvents(new PlayerListener(), this);
        getServer().getPluginManager().registerEvents(new WorldListener(), this);
    }

}
