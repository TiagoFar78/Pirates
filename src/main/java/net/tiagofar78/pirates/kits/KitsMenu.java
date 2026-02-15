package net.tiagofar78.pirates.kits;

import io.github.tiagofar78.grindstone.bukkit.BukkitPlayer;
import io.github.tiagofar78.menucompass.InventoryButton;
import io.github.tiagofar78.menucompass.InventoryGUI;

import net.kyori.adventure.text.Component;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class KitsMenu extends InventoryGUI {

    private static final int LINE_SIZE = 9;

    public KitsMenu(Player player) {
        super(player);
    }

    @Override
    public void decorate(Player player) {
        List<KitFactory> kits = KitsManager.getAvailableKits();
        int kitIndex = 0;

        outerLoop:
        for (int row = 1; row < 5; row++) {
            for (int col = 1; col < 8; col++) {
                KitFactory kit = kits.get(kitIndex);
                while (kitIndex < kits.size() && !player.hasPermission(kit.getPermission())) {
                    kitIndex++;
                    kit = kits.get(kitIndex);
                }

                if (kitIndex == kits.size()) {
                    break outerLoop;
                }

                this.addButton(row * LINE_SIZE + col, createSelectKitButton(kit));
                kitIndex++;
            }
        }

        super.decorate(player);
    }

    private InventoryButton createSelectKitButton(KitFactory kit) {
        return new InventoryButton().creator(player -> createItemIcon(player, kit)).consumer(e -> clickAction(e, kit));
    }

    private ItemStack createItemIcon(Player player, KitFactory kit) {
        ItemStack item = kit.getItemRepresentation();
        // TODO Add name
        // TODO Add description
        // TODO Glow if already selected

        return item;
    }

    private void clickAction(InventoryClickEvent e, KitFactory kit) {
        Player player = (Player) e.getWhoClicked();
        if (!player.hasPermission(kit.getPermission())) {
            BukkitPlayer.sendMessage(player, player.locale(), "pirates.selectkit.not_allowed", kit.getName());
            return;
        }

        KitsManager.assignKitToPlayer(player.getName(), kit);
        this.decorate(player);
    }

    @Override
    protected Inventory createInventory(Player player) {
        Component title = BukkitPlayer.translateMessage(player.locale(), "pirates.kitsmenu.title");
        int lines = 6;
        return Bukkit.createInventory(null, lines * LINE_SIZE, title);
    }

}
