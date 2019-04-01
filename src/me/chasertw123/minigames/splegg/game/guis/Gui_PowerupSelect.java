package me.chasertw123.minigames.splegg.game.guis;

import me.chasertw123.minigames.core.utils.gui.AbstractGui;
import me.chasertw123.minigames.shared.utils.StringUtil;
import me.chasertw123.minigames.splegg.Main;
import me.chasertw123.minigames.splegg.game.powerups.PowerupType;
import me.chasertw123.minigames.splegg.users.SpleggPlayer;
import org.bukkit.ChatColor;

/**
 * Created by Scott Hiett on 8/8/2017.
 */
public class Gui_PowerupSelect extends AbstractGui {

    public Gui_PowerupSelect(SpleggPlayer spleggPlayer) {
        super(1, "Select Powerup", spleggPlayer.getCoreUser());

        int count = 0;
        for (PowerupType powerupType : PowerupType.values()) {
            setItem(powerupType.getPowerupClass().getItemRepresentation(), count, (s, c, p) -> {
                Main.getInstance().spleggPlayerManager.get(p.getUniqueId()).setPowerupType(PowerupType.values()[s]);
                p.closeInventory();
                Main.getInstance().spleggPlayerManager.get(p.getUniqueId()).sendPrefixedMessage("Selected the Powerup "
                        + ChatColor.GOLD + "" + ChatColor.BOLD + StringUtil.niceString(powerupType.toString().replaceAll("_", " ")).toUpperCase() + ChatColor.WHITE + ".");
            });

            count++;
        }
    }

}
