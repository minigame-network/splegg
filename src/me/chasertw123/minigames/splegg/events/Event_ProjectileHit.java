package me.chasertw123.minigames.splegg.events;

import me.chasertw123.minigames.core.user.data.stats.Stat;
import me.chasertw123.minigames.splegg.Main;
import me.chasertw123.minigames.splegg.users.SpleggPlayer;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.util.BlockIterator;

/**
 * Created by Scott Hiett on 8/7/2017.
 */
public class Event_ProjectileHit implements Listener {

    @EventHandler
    public void eggHit(ProjectileHitEvent event) {
        if (event.getEntity() instanceof Egg) {
            BlockIterator iterator = new BlockIterator(event.getEntity().getWorld(), event.getEntity().getLocation().toVector(), event.getEntity().getVelocity().normalize(), 0.0D, 4);
            Block hitBlock = null;

            while (iterator.hasNext()) {
                hitBlock = iterator.next();

                if (hitBlock.getTypeId() != 0) {
                    break;
                }
            }

            Main.getInstance().mapManager.getGameWorld().playSound(hitBlock.getLocation(), Sound.CHICKEN_EGG_POP, 1, 1);

            //Send to correct class
            Main.getInstance().gameManager.currentGameMode.getGameModeClass().onEggLand(hitBlock.getLocation(),
                    (event.getEntity().getShooter() != null ? Main.getInstance().spleggPlayerManager.get(((Player)
                            event.getEntity().getShooter()).getUniqueId()) : null));

            if(event.getEntity().getShooter() != null) {
                Main.getInstance().spleggPlayerManager.get(((Player) event.getEntity().getShooter()).getUniqueId())
                        .getCoreUser().incrementStat(Stat.SPLEGG_BLOCKS_BROKEN);

                Main.getInstance().spleggPlayerManager.get(((Player) event.getEntity().getShooter()).getUniqueId())
                        .incrementBlocksBroken();
            }

            if (hitBlock.getType() == Material.TNT) {
                //spawn tnt
                Entity tnt = hitBlock.getWorld().spawn(hitBlock.getLocation(), TNTPrimed.class);
                ((TNTPrimed) tnt).setFuseTicks(1);
            }

            if(event.getEntity().getShooter() != null && Main.getInstance().gameManager.isPowerupsEnabled()){
                SpleggPlayer spleggPlayer = Main.getInstance().spleggPlayerManager.get(((Player) event.getEntity().getShooter()).getUniqueId());

                if(!spleggPlayer.isPowerupActivated())
                    spleggPlayer.addPoints(5);
                else if (spleggPlayer.getPowerupType().getPowerupClass().doesOverrideEggLand())
                    spleggPlayer.getPowerupType().getPowerupClass().onEggLand(((Egg) event.getEntity()), hitBlock, spleggPlayer);
            }
        }
    }

}
