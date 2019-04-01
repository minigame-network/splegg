package me.chasertw123.minigames.splegg.events;

import org.bukkit.entity.Chicken;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;

/**
 * Created by Scott Hiett on 8/24/2017.
 */
public class Event_EntityDamage implements Listener {

    @EventHandler
    public void onEntityDamage(EntityDamageEvent e) {
        if(e.getEntity().getType() == EntityType.CHICKEN) {
            e.setCancelled(true);
            Chicken c = (Chicken) e.getEntity();
            c.setHealth(c.getMaxHealth());
        }
    }

}
