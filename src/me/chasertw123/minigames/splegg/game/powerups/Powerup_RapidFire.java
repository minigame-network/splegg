package me.chasertw123.minigames.splegg.game.powerups;

import me.chasertw123.minigames.splegg.users.SpleggPlayer;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Egg;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Scott Hiett on 8/8/2017.
 */
public class Powerup_RapidFire extends Powerup {

    Powerup_RapidFire() {
        super("Rapid Fire", new ItemStack(Material.EGG), 7, 250, true, false);
    }

    @Override
    public void onActivation(SpleggPlayer player) {

    }

    @Override
    public void onShoot(SpleggPlayer player) {
        for(int i=0; i<3; i++)
            player.getPlayer().launchProjectile(Egg.class);
    }

    @Override
    public void onEggLand(Egg egg, Block block, SpleggPlayer shooter) {

    }

    @Override
    public void onDeactivation(SpleggPlayer player) {

    }

}
