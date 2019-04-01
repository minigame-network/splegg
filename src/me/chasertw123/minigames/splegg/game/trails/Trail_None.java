package me.chasertw123.minigames.splegg.game.trails;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Scott Hiett on 8/11/2017.
 */
public class Trail_None extends Trail {

    public Trail_None() {
        super("None", "This trail does nothing.", new ItemStack(Material.BARRIER));
    }

    @Override
    public void show(Location l) {

    }

}
