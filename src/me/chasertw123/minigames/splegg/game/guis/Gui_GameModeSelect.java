package me.chasertw123.minigames.splegg.game.guis;

import me.chasertw123.minigames.core.utils.gui.AbstractGui;
import me.chasertw123.minigames.splegg.game.modes.SpleggGameMode;
import me.chasertw123.minigames.splegg.users.SpleggPlayer;
import net.md_5.bungee.api.ChatColor;

/**
 * Created by Scott Hiett on 19/08/2017.
 */
public class Gui_GameModeSelect extends AbstractGui {

    public Gui_GameModeSelect(SpleggPlayer player) {
        super(1, "Game Mode", player.getCoreUser());

        int count = 0;

        for(SpleggGameMode gameMode : SpleggGameMode.values()) {
            setItem(gameMode.getGameModeClass().getItem(), count, (s, c, p) -> {

                player.setVotedGameMode(gameMode);
                player.sendPrefixedMessage("Voted for the gamemode " + ChatColor.GOLD + "" + ChatColor.BOLD + gameMode.getGameModeClass().getName().toUpperCase() + ChatColor.RESET + ".");
                player.getPlayer().closeInventory();
            });

            count++;
        }
    }

}
