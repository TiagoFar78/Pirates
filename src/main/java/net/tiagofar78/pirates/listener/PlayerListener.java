package net.tiagofar78.pirates.listener;

import io.github.tiagofar78.grindstone.game.GamesManager;

import net.tiagofar78.pirates.game.PiratesGame;
import net.tiagofar78.pirates.maps.PiratesMap;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class PlayerListener implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        Player player = e.getPlayer();
        String playerName = player.getName();
        if (!GamesManager.isInGame(playerName)) {
            return;
        }

        Location loc = e.getBlock().getLocation();
        PiratesGame game = (PiratesGame) GamesManager.getGame(playerName);
        PiratesMap map = (PiratesMap) game.getMap();
        if (map.isSpawnPoint(loc)) {
            game.breakSpawnPoint(playerName, loc);
        }
    }

}
