package me.chasertw123.minigames.splegg.game.trails;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Scott Hiett on 8/11/2017.
 */
public class Trail_Lover extends Trail {

    public Trail_Lover() {
        super("Lover", "Little hearts follow your eggs!", new ItemStack(Material.REDSTONE));
    }

    @Override
    public void show(Location l) {
        l.getWorld().playEffect(l, Effect.HEART, 1);
    }

}
