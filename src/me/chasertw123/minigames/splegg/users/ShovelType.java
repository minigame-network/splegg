package me.chasertw123.minigames.splegg.users;

import org.bukkit.Material;

/**
 * Created by Scott Hiett on 8/7/2017.
 */
public enum ShovelType {

    STONE(Material.STONE_SPADE), IRON(Material.IRON_SPADE), GOLD(Material.GOLD_SPADE), DIAMOND(Material.DIAMOND_SPADE);

    private Material mat;

    ShovelType(Material mat) {
        this.mat = mat;
    }

    public Material getMaterial() {
        return mat;
    }

}
