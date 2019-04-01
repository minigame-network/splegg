package me.chasertw123.minigames.splegg.game.powerups;

import me.chasertw123.minigames.splegg.users.SpleggPlayer;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Scott Hiett on 8/8/2017.
 */
public class Powerup_ScatterShot extends Powerup {

    Powerup_ScatterShot() {
        super("Scatter Shot", new ItemStack(Material.MELON_SEEDS), 5, 350, true, false);
    }

    @Override
    public void onActivation(SpleggPlayer player) {

    }

    @Override
    public void onShoot(SpleggPlayer player) {
        Player p = player.getPlayer();
        for(int i=1; i<6; i++){
            Egg egg = p.launchProjectile(Egg.class);
            Vector vector = p.getLocation().getDirection();

            List<Double> randomList = new ArrayList<>();

            for(int a=1; a<4; a++){
                double random = Math.random();
                if(random > .5)
                    random = random - .5;

                randomList.add(random);
            }

            vector.add(new Vector(randomList.get(0), randomList.get(1), randomList.get(2)));
            egg.setShooter(p);
            egg.setVelocity(vector);
        }
    }

    @Override
    public void onEggLand(Egg egg, Block block, SpleggPlayer shooter) {

    }

    @Override
    public void onDeactivation(SpleggPlayer player) {

    }
}
