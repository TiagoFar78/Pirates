package net.tiagofar78.pirates.kits;

import io.github.tiagofar78.grindstone.bukkit.BukkitPlayer;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SelectKitCommand implements CommandExecutor {

    public static final String LABEL = "kit";

    private KitFactory factory;

    public SelectKitCommand(KitFactory factory) {
        this.factory = factory;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            throw new IllegalStateException("Only players can execute this command");
        }

        Player player = (Player) sender;
        if (args.length >= 1) {
            player.performCommand(LABEL + "_" + args[0]);
            return true;
        }

        if (!player.hasPermission(factory.getPermission())) {
            BukkitPlayer.sendMessage(player, player.locale(), "pirates.selectkit.not_allowed", factory.getName());
            return true;
        }

        KitsManager.assignKitToPlayer(player.getName(), factory);
        BukkitPlayer.sendMessage(player, player.locale(), "pirates.selectkit.success", factory.getName());

        return true;
    }

}
