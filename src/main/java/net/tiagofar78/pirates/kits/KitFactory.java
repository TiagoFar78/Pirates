package net.tiagofar78.pirates.kits;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class KitFactory {

    private String permission;
    private ItemStack itemRepresentation;

    public KitFactory(String permission, String material, List<KitItem> items) {
        this.permission = permission;
        this.itemRepresentation = new ItemStack(Material.valueOf(material));
    }

    public String getPermission() {
        return permission;
    }

    public ItemStack getItemRepresentation() {
        return itemRepresentation;
    }

}
