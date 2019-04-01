package me.chasertw123.minigames.splegg.game.guis;

import me.chasertw123.minigames.core.utils.gui.AbstractGui;
import me.chasertw123.minigames.splegg.Main;
import me.chasertw123.minigames.splegg.users.SpleggPlayer;
import org.bukkit.ChatColor;
import org.bukkit.Sound;

/**
 * Created by ScottsCoding on 12/08/2017.
 */
public class Gui_PlayerTeleporter extends AbstractGui {

    public Gui_PlayerTeleporter(SpleggPlayer spleggPlayer) {
        super(3, "Player Teleporter", spleggPlayer.getCoreUser());

        int count = 0;
        for(SpleggPlayer p : Main.getInstance().spleggPlayerManager.toCollection())
            if(p.isAlive()) {
                setItem(p.getCoreUser().toItemStack().setDisplayName(ChatColor.RESET + p.getPlayer().getName()).setLore(""), count,
                        (s, c, player) -> {
                    spleggPlayer.getPlayer().teleport(p.getPlayer());
                    spleggPlayer.getPlayer().closeInventory();
                    spleggPlayer.getPlayer().playSound(spleggPlayer.getPlayer().getLocation(), Sound.CLICK, 1, 1);
                        });
            }
    }

}
