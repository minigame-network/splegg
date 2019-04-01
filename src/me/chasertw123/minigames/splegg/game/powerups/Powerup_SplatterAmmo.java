package me.chasertw123.minigames.splegg.game.powerups;

import me.chasertw123.minigames.splegg.users.SpleggPlayer;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Egg;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

/**
 * Created by Scott Hiett on 8/8/2017.
 */
public class Powerup_SplatterAmmo extends Powerup {

    Powerup_SplatterAmmo() {
        super("Splatter Ammo", new ItemStack(Material.SNOW_BALL), 5, 300, false, true);
    }

    @Override
    public void onActivation(SpleggPlayer player) {

    }

    @Override
    public void onShoot(SpleggPlayer player) {

    }

    @Override
    public void onEggLand(Egg egg, Block hitBlock, SpleggPlayer shooter) {
        Location l = hitBlock.getLocation();
        for(int i=0; i<4; i++){
            //will run this 4 times
            Egg e = (Egg) l.getWorld().spawnEntity(l, EntityType.EGG);
            e.setVelocity(new Vector(randomV(), 0.3, randomV()));
            e.setShooter(null);
        }
    }

    @Override
    public void onDeactivation(SpleggPlayer player) {

    }

    private double randomV(){
        return Math.random() * 2 - 1;
    }

}
