package me.chasertw123.minigames.splegg.game.guis;

import me.chasertw123.minigames.core.utils.gui.AbstractGui;
import me.chasertw123.minigames.shared.utils.StringUtil;
import me.chasertw123.minigames.splegg.game.trails.TrailType;
import me.chasertw123.minigames.splegg.users.SpleggPlayer;
import org.bukkit.ChatColor;

/**
 * Created by Scott Hiett on 8/11/2017.
 */
public class Gui_TrailSelect extends AbstractGui {

    public Gui_TrailSelect(SpleggPlayer spleggPlayer) {
        super(1, "Trails", spleggPlayer.getCoreUser());

        int count = 0;
        for(TrailType trailType : TrailType.values()) {
            setItem(trailType.getTrail().getItemRepresentation(), count, (s, c, p) -> {
                spleggPlayer.setTrailType(trailType);
                spleggPlayer.getPlayer().closeInventory();

                spleggPlayer.sendPrefixedMessage("Selected the Trail "
                        + ChatColor.GOLD + "" + ChatColor.BOLD + StringUtil.niceString(trailType.getTrail().getName()).toUpperCase() + ChatColor.WHITE + ".");
            });

            count++;
        }
    }

}
