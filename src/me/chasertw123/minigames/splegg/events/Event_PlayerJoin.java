package me.chasertw123.minigames.splegg.events;

import me.chasertw123.minigames.core.utils.items.cItemStack;
import me.chasertw123.minigames.shared.framework.ServerSetting;
import me.chasertw123.minigames.splegg.Main;
import me.chasertw123.minigames.splegg.game.GameManager;
import me.chasertw123.minigames.splegg.game.boards.SB_Lobby;
import me.chasertw123.minigames.splegg.users.SpleggPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * Created by Scott Hiett on 8/7/2017.
 */
public class Event_PlayerJoin implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void playerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();

        e.setJoinMessage(Main.PREFIX + ChatColor.GOLD + "" + ChatColor.BOLD + e.getPlayer().getName() + ChatColor.RESET
                + " joined the game. " + ChatColor.GRAY + "(" + Bukkit.getServer().getOnlinePlayers().size() + "/"
                + GameManager.MAX_PLAYERS + ")");

        // Create SpleggPlayer object
        SpleggPlayer player = new SpleggPlayer(p.getUniqueId());
        Main.getInstance().spleggPlayerManager.add(player);

        player.getCoreUser().setServerSetting(ServerSetting.DAMAGE, false);
        player.getCoreUser().setServerSetting(ServerSetting.HUNGER, false);
        player.getCoreUser().setScoreboard(new SB_Lobby(player));

        p.setHealth(20);
        p.setFoodLevel(20);
        p.setGameMode(GameMode.ADVENTURE);

        p.setPlayerListName(ChatColor.RESET + p.getName());

        p.setFlying(false);
        p.setAllowFlight(false);

        p.setLevel(0);
        p.setExp(0);

        p.teleport(Main.getInstance().mapManager.getLobbyWorld().getSpawnLocation().clone().add(0.5, 0, 0.5));

        p.getInventory().clear();

        p.getInventory().setItem(0, new cItemStack(Material.EMPTY_MAP, ChatColor.LIGHT_PURPLE + "Vote for Map " + ChatColor.GRAY + "(Right Click)"));
        p.getInventory().setItem(1, new cItemStack(Material.IRON_SPADE, ChatColor.GOLD + "Select Shovel " + ChatColor.GRAY + "(Right Click)"));
        p.getInventory().setItem(2, new cItemStack(Material.NETHER_STAR, ChatColor.YELLOW + "Powerup Options " + ChatColor.GRAY + "(Right Click)"));
        p.getInventory().setItem(3, new cItemStack(Material.BLAZE_POWDER, ChatColor.GREEN + "Select Powerup " + ChatColor.GRAY + "(Right Click)"));
        p.getInventory().setItem(4, new cItemStack(Material.NOTE_BLOCK, ChatColor.AQUA + "Select Trail " + ChatColor.GRAY + "(Right Click)"));
        p.getInventory().setItem(5, new cItemStack(Material.BREWING_STAND_ITEM, ChatColor.WHITE + "Select GameMode " + ChatColor.GRAY + "(Right Click)"));
        p.getInventory().setItem(8, new cItemStack(Material.BED, ChatColor.RED + "Return to Hub " + ChatColor.GRAY + "(Right Click)"));

        for (Player other : Bukkit.getServer().getOnlinePlayers())
            if (other.getUniqueId() != e.getPlayer().getUniqueId())
                other.hidePlayer(e.getPlayer());

        for (Player other : Bukkit.getServer().getOnlinePlayers())
            if (other.getUniqueId() != e.getPlayer().getUniqueId())
                other.showPlayer(e.getPlayer());
    }

}
