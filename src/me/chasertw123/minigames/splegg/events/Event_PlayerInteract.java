package me.chasertw123.minigames.splegg.events;

import me.chasertw123.minigames.core.api.misc.Title;
import me.chasertw123.minigames.core.user.data.stats.Stat;
import me.chasertw123.minigames.splegg.Main;
import me.chasertw123.minigames.splegg.game.guis.*;
import me.chasertw123.minigames.splegg.game.states.GameState;
import me.chasertw123.minigames.splegg.users.SpleggPlayer;
import org.bukkit.*;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.BlockIterator;

/**
 * Created by Scott Hiett on 8/7/2017.
 */
public class Event_PlayerInteract implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        if (Main.getInstance().gameManager.getGameState() == GameState.GAME && e.getPlayer().getItemInHand() != null
                && e.getPlayer().getItemInHand().getType() == Main.getInstance().spleggPlayerManager.get(e.getPlayer().getUniqueId()).getShovelType().getMaterial()
                && (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) && Main.getInstance().spleggPlayerManager.get(e.getPlayer().getUniqueId()).canShoot()) {

            e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.GHAST_FIREBALL, 0.2F, 2);

            Main.getInstance().spleggPlayerManager.get(e.getPlayer().getUniqueId()).getCoreUser().incrementStat(Stat.SPLEGG_EGGS_SHOT);

            //Pass the event onto the current gamemode.
            Main.getInstance().gameManager.currentGameMode.getGameModeClass().onShoot(Main.getInstance().spleggPlayerManager.get(e.getPlayer().getUniqueId()));

            if(Main.getInstance().gameManager.isPowerupsEnabled() && Main.getInstance().spleggPlayerManager.get(e.getPlayer().getUniqueId()).isPowerupActivated()
                    && Main.getInstance().spleggPlayerManager.get(e.getPlayer().getUniqueId()).getPowerupType().getPowerupClass().doesOverrideOnShoot()){

                Main.getInstance().spleggPlayerManager.get(e.getPlayer().getUniqueId()).getPowerupType().getPowerupClass()
                        .onShoot(Main.getInstance().spleggPlayerManager.get(e.getPlayer().getUniqueId()));

                return;
            }

            e.setCancelled(true);

            e.getPlayer().launchProjectile(Egg.class);

            Player p = e.getPlayer();

            BlockIterator blocksToAdd = new BlockIterator(p.getEyeLocation(), 0D, 15);
            Location blockToAdd;
            int i = 1;
            while(blocksToAdd.hasNext()){
                blockToAdd = blocksToAdd.next().getLocation();
                if(blockToAdd.getBlock().getType() != Material.AIR)
                    break;

                if(i == 12){
                    i = 1;

                    //Show the effect
                    Main.getInstance().spleggPlayerManager.get(p.getUniqueId()).getTrailType().getTrail().show(blockToAdd);
                }else if(i < 12)
                    i++;
            }
        }

        else if (Main.getInstance().gameManager.getGameState() == GameState.GAME && e.getPlayer().getItemInHand() != null
                && e.getPlayer().getItemInHand().getType() == Main.getInstance().spleggPlayerManager.get(e.getPlayer().getUniqueId()).getShovelType().getMaterial()
                && (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) && Main.getInstance().gameManager.isPowerupsEnabled()) {

            e.setCancelled(true);

            SpleggPlayer player = Main.getInstance().spleggPlayerManager.get(e.getPlayer().getUniqueId());

            if(player.getPoints() >= player.getPowerupType().getPowerupClass().getPointsToGet()) {
                player.sendPrefixedMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "Powerup Activated!");

                player.getCoreUser().incrementStat(Stat.SPLEGG_POWERUPS_COLLECTED);

                player.setPowerupActivated(true);
                player.getPowerupType().getPowerupClass().onActivation(player);
                player.setPoints(0);

                //wait the delay to end it!

                Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> {
                    player.sendPrefixedMessage(ChatColor.RED + "Powerup Deactivated.");
                    player.getPowerupType().getPowerupClass().onDeactivation(player);
                    player.setPowerupActivated(false);
                }, 20 * player.getPowerupType().getPowerupClass().getDuration());
            } else {
                Title.sendActionbar(player.getPlayer(), ChatColor.RED + "Not Enough Points to Activate Powerup!");
            }
        }

        else if (Main.getInstance().gameManager.getGameState() == GameState.LOBBY && e.getPlayer().getItemInHand() != null
                && e.getPlayer().getItemInHand().getType() == Material.IRON_SPADE) {

            e.setCancelled(true);

            Main.getInstance().spleggPlayerManager.get(e.getPlayer().getUniqueId()).getCoreUser()
                    .setCurrentGui(new Gui_ShovelSelect(e.getPlayer()));
        }

        else if (Main.getInstance().gameManager.getGameState() == GameState.LOBBY && e.getPlayer().getItemInHand() != null
                && e.getPlayer().getItemInHand().getType() == Material.BED) {

            e.setCancelled(true);

            e.getPlayer().performCommand("hub");
        }

        else if (Main.getInstance().gameManager.getGameState() == GameState.LOBBY && e.getPlayer().getItemInHand() != null
                && e.getPlayer().getItemInHand().getType() == Material.NOTE_BLOCK) {

            e.setCancelled(true);

            Main.getInstance().spleggPlayerManager.get(e.getPlayer().getUniqueId()).getCoreUser()
                    .setCurrentGui(new Gui_TrailSelect(Main.getInstance().spleggPlayerManager.get(e.getPlayer().getUniqueId())));
        }

        else if (Main.getInstance().gameManager.getGameState() == GameState.LOBBY && e.getPlayer().getItemInHand() != null
                && e.getPlayer().getItemInHand().getType() == Material.EMPTY_MAP) {

            e.setCancelled(true);

            Main.getInstance().spleggPlayerManager.get(e.getPlayer().getUniqueId()).getCoreUser()
                    .setCurrentGui(new Gui_MapVote(Main.getInstance().spleggPlayerManager.get(e.getPlayer().getUniqueId())));
        }

        else if (Main.getInstance().gameManager.getGameState() == GameState.LOBBY && e.getPlayer().getItemInHand() != null
                && e.getPlayer().getItemInHand().getType() == Material.BREWING_STAND_ITEM) {

            e.setCancelled(true);

            Main.getInstance().spleggPlayerManager.get(e.getPlayer().getUniqueId()).getCoreUser()
                    .setCurrentGui(new Gui_GameModeSelect(Main.getInstance().spleggPlayerManager.get(e.getPlayer().getUniqueId())));
        }

        else if (Main.getInstance().gameManager.getGameState() == GameState.LOBBY && e.getPlayer().getItemInHand() != null
                && e.getPlayer().getItemInHand().getType() == Material.NETHER_STAR) {

            e.setCancelled(true);

            Main.getInstance().spleggPlayerManager.get(e.getPlayer().getUniqueId()).getCoreUser()
                    .setCurrentGui(new Gui_PowerupOptions(Main.getInstance().spleggPlayerManager.get(e.getPlayer().getUniqueId())));
        }

        else if (Main.getInstance().gameManager.getGameState() == GameState.LOBBY && e.getPlayer().getItemInHand() != null
                && e.getPlayer().getItemInHand().getType() == Material.BLAZE_POWDER) {

            e.setCancelled(true);

            Main.getInstance().spleggPlayerManager.get(e.getPlayer().getUniqueId()).getCoreUser()
                    .setCurrentGui(new Gui_PowerupSelect(Main.getInstance().spleggPlayerManager.get(e.getPlayer().getUniqueId())));
        }

        else if (Main.getInstance().gameManager.getGameState() == GameState.GAME && Main.getInstance().spleggPlayerManager.get(e.getPlayer().getUniqueId()).isDead()
                && e.getPlayer().getItemInHand() != null && e.getPlayer().getItemInHand().getType() == Material.COMPASS) {

            e.setCancelled(true);

            Main.getInstance().spleggPlayerManager.get(e.getPlayer().getUniqueId()).getCoreUser()
                    .setCurrentGui(new Gui_PlayerTeleporter(Main.getInstance().spleggPlayerManager.get(e.getPlayer().getUniqueId())));
        }
    }

}
