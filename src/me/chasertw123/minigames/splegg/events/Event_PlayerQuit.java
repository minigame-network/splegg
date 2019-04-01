package me.chasertw123.minigames.splegg.events;

import me.chasertw123.minigames.splegg.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Created by Scott Hiett on 8/7/2017.
 */
public class Event_PlayerQuit implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void playerQuit(PlayerQuitEvent e) {
        e.setQuitMessage(null);

        Main.getInstance().spleggPlayerManager.get(e.getPlayer().getUniqueId()).updatePlaytime();

        Main.getInstance().spleggPlayerManager.remove(e.getPlayer().getUniqueId());
    }

}
