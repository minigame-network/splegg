package me.chasertw123.minigames.splegg.events;

import me.chasertw123.minigames.splegg.Main;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

import java.util.List;

/**
 * Created by Scott Hiett on 8/8/2017.
 */
public class Event_EntityExplode implements Listener {

    @EventHandler
    public void entityExplode(EntityExplodeEvent e) {
        List<Block> blocks = e.blockList();

        e.setCancelled(true);

        Main.getInstance().gameManager.currentGameMode.getGameModeClass().onTNTExplode(blocks);
    }

}
