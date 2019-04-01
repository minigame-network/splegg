package me.chasertw123.minigames.splegg.game.powerups;

import me.chasertw123.minigames.splegg.Main;
import me.chasertw123.minigames.splegg.users.SpleggPlayer;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Egg;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.inventory.ItemStack;

/**
 * Created by ScottsCoding on 12/08/2017.
 */
public class Powerup_TNTRain extends Powerup {

    Powerup_TNTRain() {
        super("TNT Rain", new ItemStack(Material.TNT), 1, 400, false, false);
    }

    @Override
    public void onActivation(SpleggPlayer player) {
        for(SpleggPlayer spleggPlayer : Main.getInstance().spleggPlayerManager.toCollection())
            if(spleggPlayer.isAlive() && spleggPlayer.getUuid() != player.getUuid()) {
                //Lets have some fun
                TNTPrimed tnt = spleggPlayer.getPlayer().getWorld().spawn(spleggPlayer.getPlayer().getLocation(),
                        TNTPrimed.class);
                tnt.setFuseTicks(20 * 5);
            }
    }

    @Override
    public void onShoot(SpleggPlayer player) {

    }

    @Override
    public void onEggLand(Egg egg, Block hitBlock, SpleggPlayer shooter) {

    }

    @Override
    public void onDeactivation(SpleggPlayer player) {

    }
}
