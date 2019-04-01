package me.chasertw123.minigames.splegg.game.loops;

import me.chasertw123.minigames.core.api.misc.Title;
import me.chasertw123.minigames.splegg.Main;
import me.chasertw123.minigames.splegg.game.GameManager;
import me.chasertw123.minigames.splegg.game.modes.SpleggGameMode;
import me.chasertw123.minigames.splegg.users.SpleggPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Random;

/**
 * Created by Scott Hiett on 8/7/2017.
 */
public class Loop_Lobby extends GameLoop {

    public Loop_Lobby() {
        super(60, 20);
    }

    @Override
    public void run() {
        Main.getInstance().mapManager.getLobbyWorld().setStorm(false);
        Main.getInstance().mapManager.getLobbyWorld().setThundering(false);

        int needed = GameManager.MIN_PLAYERS - Bukkit.getOnlinePlayers().size();
        if (needed > 0) {
            for (Player player : Bukkit.getOnlinePlayers())
                Title.sendActionbar(player, ChatColor.GOLD + "" + ChatColor.BOLD + "(!) " + ChatColor.YELLOW
                        + "Waiting for " + ChatColor.GOLD + needed + ChatColor.YELLOW + " more player" + (needed > 1 ? "s" : ""));

        } else if (interval < 60 && GameManager.MIN_PLAYERS > Bukkit.getOnlinePlayers().size()) {
            interval = 60;

            for (Player player : Bukkit.getOnlinePlayers())
                Title.sendActionbar(player, ChatColor.GOLD + "" + ChatColor.BOLD + "(!) " + ChatColor.YELLOW + "Game canceled due to lack of players :(");
        }

        if (GameManager.MIN_PLAYERS <= Bukkit.getOnlinePlayers().size()) {
            if (Bukkit.getOnlinePlayers().size() >= (GameManager.MAX_PLAYERS * 0.75) && interval > 20) {
                interval = 15;

                for (Player player : Bukkit.getOnlinePlayers())
                    player.sendMessage(Main.PREFIX + ChatColor.YELLOW + "Countdown reduced to 30 seconds due to lots of players!");
            }

            if (interval <= 5 && Main.getInstance().gameManager.voteManager.isVotingActive()) {

                String map = Main.getInstance().gameManager.voteManager.getWinner();

                SpleggGameMode gameMode = SpleggGameMode.STANDARD;
                int gameModeVoteCount = 0;

                for(SpleggGameMode spleggGameMode : SpleggGameMode.values()) {
                    int count = 0;

                    for(SpleggPlayer spleggPlayer : Main.getInstance().spleggPlayerManager.toCollection())
                        if(spleggPlayer.getVotedGameMode() == spleggGameMode)
                            count++;

                    if(count > gameModeVoteCount) {
                        gameModeVoteCount = count;
                        gameMode = spleggGameMode;
                    }
                }

                Main.getInstance().gameManager.setCurrentGameMode(gameMode);

                int yes = 0, no = 0;

                for(SpleggPlayer spleggPlayer : Main.getInstance().spleggPlayerManager.toCollection()){
                    if(spleggPlayer.hasVotedEnablePowerups())
                        continue;

                    if(spleggPlayer.wantsPowerups())
                        yes++;
                    else
                        no++;
                }

                Main.getInstance().gameManager.setPowerupsEnabled((no == 0 && yes == 0) || (yes == no ? new Random().nextBoolean() : yes > no));

                for (Player player : Bukkit.getOnlinePlayers())
                    player.sendMessage(Main.PREFIX + ChatColor.YELLOW + "Voting has ended! The selected map is "
                            + ChatColor.GOLD + ""+ ChatColor.BOLD + map.toUpperCase() + ChatColor.YELLOW + ", the voted GameMode is "
                            + ChatColor.GOLD + "" + ChatColor.BOLD + gameMode.getGameModeClass().getName().toUpperCase() + ChatColor.YELLOW + " and Powerups are " + ChatColor.GOLD + ""
                            + ChatColor.BOLD + (Main.getInstance().gameManager.isPowerupsEnabled() ? "ENABLED" : "DISABLED"));

                Main.getInstance().mapManager.loadMap(map);
            }

            for (Player player : Bukkit.getOnlinePlayers())
                Title.sendActionbar(player, ChatColor.GOLD + "" + ChatColor.BOLD + "(!) " + ChatColor.YELLOW + "Game starting in " + ChatColor.GOLD + interval + "s...");

            if (interval-- <= 0) {
                this.cancel();
                Main.getInstance().gameManager.startGame();
            }
        }
    }

}
