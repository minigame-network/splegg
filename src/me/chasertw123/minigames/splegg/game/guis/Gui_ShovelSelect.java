package me.chasertw123.minigames.splegg.game.guis;

import me.chasertw123.minigames.core.utils.gui.AbstractGui;
import me.chasertw123.minigames.core.utils.items.cItemStack;
import me.chasertw123.minigames.shared.utils.StringUtil;
import me.chasertw123.minigames.splegg.Main;
import me.chasertw123.minigames.splegg.users.ShovelType;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * Created by Scott Hiett on 8/7/2017.
 */
public class Gui_ShovelSelect extends AbstractGui {

    public Gui_ShovelSelect(Player player) {
        super(1, "Shovel Selector", Main.getInstance().spleggPlayerManager.get(player.getUniqueId()).getCoreUser());

        int count = 0;
        for (ShovelType shovelType : ShovelType.values()) {
            setItem(new cItemStack(shovelType.getMaterial(), ChatColor.RESET + StringUtil.niceString(shovelType.toString())), count, (s, c, p) -> {
                Main.getInstance().spleggPlayerManager.get(p.getUniqueId()).setShovelType(ShovelType.values()[s]);
                p.closeInventory();
                Main.getInstance().spleggPlayerManager.get(p.getUniqueId()).sendPrefixedMessage("Selected the Shovel " + ChatColor.GOLD + "" + ChatColor.BOLD + StringUtil.niceString(shovelType.toString()).toUpperCase() + ChatColor.WHITE + ".");
            });

            count++;
        }
    }

}
