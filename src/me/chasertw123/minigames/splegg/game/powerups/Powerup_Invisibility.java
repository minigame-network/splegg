package me.chasertw123.minigames.splegg.game.powerups;

import me.chasertw123.minigames.splegg.users.SpleggPlayer;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Egg;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * Created by Scott Hiett on 8/8/2017.
 */
public class Powerup_Invisibility extends Powerup {

    Powerup_Invisibility() {
        super("Invisibility", new ItemStack(Material.GLASS), 10, 300, false, false);
    }

    @Override
    public void onActivation(SpleggPlayer player) {
        player.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 20 * getDuration(),
                2, true, false));
    }

    @Override
    public void onShoot(SpleggPlayer player) {

    }

    @Override
    public void onEggLand(Egg egg, Block block, SpleggPlayer shooter) {

    }

    @Override
    public void onDeactivation(SpleggPlayer player) {

    }

}
