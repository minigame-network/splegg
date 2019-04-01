package me.chasertw123.minigames.splegg.game.trails;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Scott Hiett on 8/11/2017.
 */
public class Trail_Artist extends Trail {

    public Trail_Artist() {
        super("Artist", "Random color note particles that follow your eggs!", new ItemStack(Material.NOTE_BLOCK));
    }

    @Override
    public void show(Location l) {
        l.getWorld().playEffect(l, Effect.NOTE, 1);
    }

}
