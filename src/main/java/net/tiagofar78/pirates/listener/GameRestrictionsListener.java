package net.tiagofar78.pirates.listener;

import io.github.tiagofar78.grindstone.game.GamesManager;
import io.github.tiagofar78.grindstone.game.MinigamePlayer;

import net.tiagofar78.pirates.game.PiratesGame;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class GameRestrictionsListener implements Listener {

    @EventHandler
    public void cancelFriendlyFire(EntityDamageByEntityEvent e) {
        Player entity = (Player) e.getEntity();
        String damagedName = entity.getName();
        String damagerName = e.getDamager().getName();
        PiratesGame game = GamesManager.getGame(damagerName, PiratesGame.class);
        if (game == null || game != GamesManager.getGame(damagerName, PiratesGame.class)) {
            return;
        }

        MinigamePlayer damaged = game.getPlayer(damagedName);
        MinigamePlayer damager = game.getPlayer(damagerName);
        if (game.getTeamIndex(damaged) == game.getTeamIndex(damager)) {
            e.setCancelled(true);
        }
    }

    private boolean isInPiratesGame(String playerName) {
        return GamesManager.getGame(playerName, PiratesGame.class) != null;
    }

    @EventHandler
    public void stopHunger(FoodLevelChangeEvent e) {
        if (isInPiratesGame(e.getEntity().getName())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void avoidBlockBreak(BlockBreakEvent e) {
        if (isInPiratesGame(e.getPlayer().getName())) {
            e.setCancelled(true);
        }
    }

}
