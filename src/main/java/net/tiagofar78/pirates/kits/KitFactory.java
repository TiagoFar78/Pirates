package net.tiagofar78.pirates.kits;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class KitFactory {

    private String name;
    private String permission;
    private ItemStack itemRepresentation;

    public KitFactory(String name, String permission, String material, List<KitItem> items) {
        this.name = name;
        this.permission = permission;
        this.itemRepresentation = new ItemStack(Material.valueOf(material));
    }

    public String getName() {
        return name;
    }

    public String getPermission() {
        return permission;
    }

    public ItemStack getItemRepresentation() {
        return itemRepresentation;
    }

}
