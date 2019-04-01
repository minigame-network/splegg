package me.chasertw123.minigames.splegg.game.guis;

import me.chasertw123.minigames.core.utils.gui.AbstractGui;
import me.chasertw123.minigames.core.utils.items.cItemStack;
import me.chasertw123.minigames.splegg.Main;
import me.chasertw123.minigames.splegg.users.SpleggPlayer;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;

/**
 * Created by Scott Hiett on 8/8/2017.
 */
public class Gui_PowerupOptions extends AbstractGui {

    public Gui_PowerupOptions(SpleggPlayer user) {
        super(1, "Enable Powerups?", user.getCoreUser());

        setItem(new cItemStack(Material.EMERALD_BLOCK, ChatColor.GREEN + "" + ChatColor.BOLD + "Yes"), 2, (s, c, p) -> {
            if (!Main.getInstance().gameManager.voteManager.isVotingActive()) {
                user.getCoreUser().getPlayer().sendMessage(Main.PREFIX + ChatColor.RED + "Voting has already ended!");
                user.getCoreUser().getPlayer().playSound(user.getPlayer().getLocation(), Sound.VILLAGER_NO, 1F, 1F);
                user.getCoreUser().getPlayer().closeInventory();
                return;
            }

            Main.getInstance().spleggPlayerManager.get(p.getUniqueId()).setWantsPowerups(true);
            Main.getInstance().spleggPlayerManager.get(p.getUniqueId()).sendPrefixedMessage("Voted for Powerups to be " + ChatColor.GOLD + "" + ChatColor.BOLD + "ENABLED" + ChatColor.RESET + ".");

            p.closeInventory();
        });

        setItem(new cItemStack(Material.REDSTONE_BLOCK, ChatColor.RED + "" + ChatColor.BOLD + "No"), 6, (s, c, p) -> {
            if (!Main.getInstance().gameManager.voteManager.isVotingActive()) {
                user.getCoreUser().getPlayer().sendMessage(Main.PREFIX + ChatColor.RED + "Voting has already ended!");
                user.getCoreUser().getPlayer().playSound(user.getPlayer().getLocation(), Sound.VILLAGER_NO, 1F, 1F);
                user.getCoreUser().getPlayer().closeInventory();
                return;
            }

            Main.getInstance().spleggPlayerManager.get(p.getUniqueId()).setWantsPowerups(false);
            Main.getInstance().spleggPlayerManager.get(p.getUniqueId()).sendPrefixedMessage("Voted for Powerups to be " + ChatColor.GOLD + "" + ChatColor.BOLD + "DISABLED" + ChatColor.RESET + ".");

            p.closeInventory();
        });
    }

}
