package me.chasertw123.minigames.splegg.events;

import me.chasertw123.minigames.core.user.data.achievements.Achievement;
import me.chasertw123.minigames.splegg.Main;
import me.chasertw123.minigames.splegg.game.states.GameState;
import me.chasertw123.minigames.splegg.users.SpleggPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

/**
 * Created by Scott Hiett on 8/7/2017.
 */
public class Event_PlayerMove implements Listener {

    int deadCount = 0;

    @EventHandler
    public void playerMoveEvent(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        SpleggPlayer spleggPlayer = Main.getInstance().spleggPlayerManager.get(p.getUniqueId());

        if (Main.getInstance().gameManager.getGameState() == GameState.GAME && p.getLocation().getY() < 0 && spleggPlayer.isAlive()) {
            Main.getInstance().gameManager.currentGameMode.getGameModeClass().onPlayerDeath(spleggPlayer);

            if(deadCount == 0) {
                spleggPlayer.getCoreUser().unlockAchievement(Achievement.SPLEGG_FIRST_TO_DIE);
            }

            deadCount++;

            Main.getInstance().gameManager.checkGame();
        }
    }

}
