package net.tiagofar78.pirates.kits;

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
            // TODO send not allowed message
            return true;
        }

        KitsManager.assignKitToPlayer(player.getName(), factory);
        // TODO send success message

        return true;
    }

}
