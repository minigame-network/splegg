package me.chasertw123.minigames.splegg.events;

import me.chasertw123.minigames.core.api.misc.Title;
import me.chasertw123.minigames.splegg.Main;
import me.chasertw123.minigames.splegg.game.loops.Loop_TweetsMove;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;

/**
 * Created by Scott Hiett on 8/9/2017.
 */
public class Event_CreatureSpawn implements Listener {

    private static Random random = new Random();

    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent e){
        if(e.getSpawnReason() != CreatureSpawnEvent.SpawnReason.EGG)
            return;

        if(random.nextInt(100) == 26 && !Loop_TweetsMove.isTweetsAlive) {
            e.getEntity().teleport(e.getEntity().getLocation().clone().add(0, 2, 0));
            Loop_TweetsMove.isTweetsAlive = true;
            Loop_TweetsMove.tweets = (Chicken) e.getEntity();

            new Loop_TweetsMove();

            for(Player player : Bukkit.getServer().getOnlinePlayers())
                Title.sendTitle(player, 1, 2, 1, ChatColor.GREEN + "" + ChatColor.BOLD + "TWEETS THE CHICKEN HAS SPAWNED!");

            Main.getInstance().mapManager.getGameWorld().strikeLightningEffect(e.getEntity().getLocation());

            ((Chicken) e.getEntity()).setAdult();

            e.getEntity().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 20,
                    2, true, true));
        } else {
            e.setCancelled(true);
        }

    }

}
