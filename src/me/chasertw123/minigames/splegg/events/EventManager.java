package me.chasertw123.minigames.splegg.events;

import me.chasertw123.minigames.splegg.Main;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

/**
 * Created by Scott Hiett on 8/7/2017.
 */
public class EventManager {

    public EventManager() {
        Listener[] listeners = {
                new Event_PlayerJoin(),
                new Event_PlayerQuit(),
                new Event_PlayerInteract(),
                new Event_PlayerMove(),
                new Event_ProjectileHit(),
                new Event_EntityExplode(),
                new Event_CreatureSpawn(),
                new Event_EntityDamage()
        };

        for (Listener l : listeners)
            Bukkit.getServer().getPluginManager().registerEvents(l, Main.getInstance());
    }

}
