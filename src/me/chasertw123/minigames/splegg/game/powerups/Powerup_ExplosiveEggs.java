package me.chasertw123.minigames.splegg.game.powerups;

import me.chasertw123.minigames.splegg.users.SpleggPlayer;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Egg;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

/**
 * Created by ScottsCoding on 12/08/2017.
 */
public class Powerup_ExplosiveEggs extends Powerup {

    private static Random random = new Random();

    Powerup_ExplosiveEggs() {
        super("Explosive Eggs", new ItemStack(Material.DRAGON_EGG), 10, 400, false, true);
    }

    @Override
    public void onActivation(SpleggPlayer player) {

    }

    @Override
    public void onShoot(SpleggPlayer player) {

    }

    @Override
    public void onEggLand(Egg egg, Block hitBlock, SpleggPlayer shooter) {
        if(random.nextInt(5) == 3) {
            TNTPrimed tnt = hitBlock.getLocation().getWorld().spawn(hitBlock.getLocation(), TNTPrimed.class);
            tnt.setFuseTicks(1);
        }
    }

    @Override
    public void onDeactivation(SpleggPlayer player) {

    }

}
