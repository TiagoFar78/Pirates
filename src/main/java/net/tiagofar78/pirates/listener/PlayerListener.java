package net.tiagofar78.pirates.listener;

import io.github.tiagofar78.grindstone.game.GamesManager;

import net.tiagofar78.pirates.game.PiratesGame;
import net.tiagofar78.pirates.maps.PiratesMap;

import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class PlayerListener implements Listener {

    @EventHandler(ignoreCancelled = false)
    public void onBlockBreak(BlockBreakEvent e) {
        String playerName = e.getPlayer().getName();
        PiratesGame game = GamesManager.getGame(playerName, PiratesGame.class);
        if (game == null) {
            return;
        }

        Location loc = e.getBlock().getLocation();
        PiratesMap map = (PiratesMap) game.getMap();
        if (map.isSpawnPoint(loc)) {
            game.breakSpawnPoint(playerName, loc);
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
    public void onHit(EntityDamageByEntityEvent e) {
        Player entity = (Player) e.getEntity();
        String damagedName = entity.getName();
        String damagerName = e.getDamager().getName();
        PiratesGame game = GamesManager.getGame(damagerName, PiratesGame.class);
        if (game == null || game != GamesManager.getGame(damagerName, PiratesGame.class)) {
            return;
        }

        if (e.getFinalDamage() >= entity.getHealth()) {
            e.setCancelled(true);
            double maxHealth = entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
            entity.setHealth(maxHealth);
            game.kill(damagerName, damagedName);
        }
    }

}
