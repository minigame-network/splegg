package me.chasertw123.minigames.splegg.game.loops;

import me.chasertw123.minigames.core.api.misc.Title;
import me.chasertw123.minigames.core.utils.items.cItemStack;
import me.chasertw123.minigames.splegg.Main;
import me.chasertw123.minigames.splegg.users.SpleggPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

/**
 * Created by Scott Hiett on 8/11/2017.
 */
public class Loop_EggLauncher extends GameLoop {

    public Loop_EggLauncher() {
        super(15, 20);
    }

    @Override
    public void run() {

        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            Title.sendTitle(player, 1, 20, 1, ChatColor.YELLOW + "" + ChatColor.BOLD + "" + interval);
            player.playSound(player.getLocation(), Sound.CLICK, 2F, 2F);
        }

        if (interval-- == 0) {
            for (SpleggPlayer spleggPlayer : Main.getInstance().spleggPlayerManager.toCollection()) {
                spleggPlayer.getPlayer().getInventory().clear();
                spleggPlayer.getPlayer().getInventory().addItem(new cItemStack(spleggPlayer.getShovelType().getMaterial(), ChatColor.WHITE + "Egg Launcher"));
                Title.sendTitles(spleggPlayer.getPlayer(), 0, 20, 0, ChatColor.GREEN + "" + ChatColor.BOLD + "GO", ChatColor.GOLD
                        + "" + ChatColor.ITALIC + Main.getInstance().gameManager.getGameMap().getName() + " built by " + Main.getInstance().gameManager.getGameMap()
                        .getBuilders().toString().substring(1).replaceAll("]", ""));
                spleggPlayer.getPlayer().playSound(spleggPlayer.getPlayer().getLocation(), Sound.ENDERDRAGON_GROWL, 1.25F, 1.25F);
            }

            this.cancel();
        }
    }

}
