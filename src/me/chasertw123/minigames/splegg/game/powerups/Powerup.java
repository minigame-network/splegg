package me.chasertw123.minigames.splegg.game.powerups;

import me.chasertw123.minigames.core.utils.items.cItemStack;
import me.chasertw123.minigames.splegg.users.SpleggPlayer;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Egg;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Scott Hiett on 8/8/2017.
 */
public abstract class Powerup {

    private String name;
    private ItemStack itemRepresentation;
    private int duration, pointsToGet;
    private boolean overrideOnShoot, overrideEggLand;

    Powerup(String name, ItemStack itemRepresentation, int duration, int pointsToGet, boolean overrideOnShoot, boolean overrideEggLand) {
        this.name = name;
        this.itemRepresentation = itemRepresentation;
        this.duration = duration;
        this.pointsToGet = pointsToGet;
        this.overrideEggLand = overrideEggLand;
        this.overrideOnShoot = overrideOnShoot;
    }

    public boolean doesOverrideEggLand() {
        return overrideEggLand;
    }

    public boolean doesOverrideOnShoot() {
        return overrideOnShoot;
    }

    public int getDuration() {
        return duration;
    }

    public int getPointsToGet() {
        return pointsToGet;
    }

    public ItemStack getItemRepresentation() {
        return new cItemStack(itemRepresentation).setDisplayName(ChatColor.RESET + name);
    }

    public abstract void onActivation(SpleggPlayer player);

    public abstract void onShoot(SpleggPlayer player);

    public abstract void onEggLand(Egg egg, Block hitBlock, SpleggPlayer shooter);

    public abstract void onDeactivation(SpleggPlayer player);

}
