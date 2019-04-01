package me.chasertw123.minigames.splegg.game.guis;

import me.chasertw123.minigames.core.utils.gui.AbstractGui;
import me.chasertw123.minigames.core.utils.items.cItemStack;
import me.chasertw123.minigames.splegg.Main;
import me.chasertw123.minigames.splegg.maps.BaseMap;
import me.chasertw123.minigames.splegg.users.SpleggPlayer;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;

/**

 * Created by Scott Hiett on 8/8/2017.
 */
public class Gui_MapVote extends AbstractGui {
    public Gui_MapVote(SpleggPlayer user) {
        super(1, "Vote for a map!", user.getCoreUser());

        int i = 1;
        for (BaseMap map : Main.getInstance().mapManager.getMaps()) {
            setItem(new cItemStack(Material.EMPTY_MAP, ChatColor.YELLOW + map.getName() + ChatColor.WHITE + " [" + ChatColor.GREEN
                    + Main.getInstance().gameManager.voteManager.getVotes(map) + ChatColor.WHITE + "]").addFancyLore(ChatColor.YELLOW + "" + ChatColor.ITALIC + map.getDescription(), ChatColor.YELLOW.toString()), i, (s, c, p) -> {

                if (!Main.getInstance().gameManager.voteManager.isVotingActive()) {
                    user.getCoreUser().getPlayer().sendMessage(Main.PREFIX + ChatColor.RED + "Voting has already ended!");
                    user.getCoreUser().getPlayer().playSound(user.getPlayer().getLocation(), Sound.VILLAGER_NO, 1F, 1F);
                    user.getCoreUser().getPlayer().closeInventory();

                    return;
                }

                user.setVotedMap(map.getName());
                user.getCoreUser().getPlayer().sendMessage(Main.PREFIX + ChatColor.WHITE + "You voted for the map " + ChatColor.GOLD + "" + ChatColor.BOLD + map.getName().toUpperCase() + ChatColor.WHITE + ".");
                user.getCoreUser().getPlayer().playSound(user.getPlayer().getLocation(), Sound.VILLAGER_YES, 1F, 1F);
                user.getCoreUser().getPlayer().closeInventory();
            });

            i += 3;
        }
    }

}