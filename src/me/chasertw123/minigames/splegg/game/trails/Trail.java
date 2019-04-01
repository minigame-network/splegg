package me.chasertw123.minigames.splegg.game.trails;

import me.chasertw123.minigames.core.utils.items.cItemStack;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Scott Hiett on 8/11/2017.
 */
public abstract class Trail {

    private String name;
    private String description;
    private ItemStack itemRepresentation;

    public Trail(String name, String description, ItemStack itemRepresentation) {
        this.name = name;
        this.description = description;
        this.itemRepresentation = itemRepresentation;
    }

    public ItemStack getItemRepresentation() {
        return new cItemStack(itemRepresentation).setDisplayName(ChatColor.RESET + name).addFancyLore(description, ChatColor.YELLOW.toString());
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public abstract void show(Location l);

}
