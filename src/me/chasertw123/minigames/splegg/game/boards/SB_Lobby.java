package me.chasertw123.minigames.splegg.game.boards;

import me.chasertw123.minigames.core.utils.scoreboard.Entry;
import me.chasertw123.minigames.core.utils.scoreboard.EntryBuilder;
import me.chasertw123.minigames.core.utils.scoreboard.ScoreboardHandler;
import me.chasertw123.minigames.core.utils.scoreboard.SimpleScoreboard;
import me.chasertw123.minigames.shared.utils.StringUtil;
import me.chasertw123.minigames.splegg.Main;
import me.chasertw123.minigames.splegg.game.GameManager;
import me.chasertw123.minigames.splegg.game.modes.SpleggGameMode;
import me.chasertw123.minigames.splegg.maps.BaseMap;
import me.chasertw123.minigames.splegg.users.SpleggPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;

public class SB_Lobby extends SimpleScoreboard {

    public SB_Lobby(SpleggPlayer spleggPlayer) {
        super(spleggPlayer.getPlayer());

        this.setUpdateInterval(1L);
        this.setHandler(new ScoreboardHandler() {
            @Override
            public String getTitle(Player player) {
                int time = Main.getInstance().gameManager.lobbyLoop.interval, minutes = time / 60, seconds = time % 60;
                String mins = (minutes < 10 ? "0" : "") + minutes, secs = (seconds < 10 ? "0" : "") + seconds;

                if(Bukkit.getServer().getOnlinePlayers().size() == 1)
                    return ChatColor.GOLD + "" + ChatColor.BOLD + "Splegg"; // Don't show time

                return ChatColor.GOLD + "" + ChatColor.BOLD + "Splegg " + ChatColor.GREEN + "" + mins + ":" + secs;
            }

            @Override
            public List<Entry> getEntries(Player player) {

                EntryBuilder eb = new EntryBuilder();

                eb.blank();
                eb.next(ChatColor.BLUE + "Players: " + ChatColor.WHITE + Bukkit.getServer().getOnlinePlayers().size() + "/" + GameManager.MAX_PLAYERS);
                eb.blank();
                eb.next(ChatColor.GREEN + "Shovel: " + ChatColor.WHITE + StringUtil.niceString(spleggPlayer.getShovelType().toString().replaceAll("_", " ")));
                eb.next(ChatColor.GREEN + "Powerups On: " + ChatColor.WHITE + (!spleggPlayer.hasVotedEnablePowerups() ? "Unvoted" : (spleggPlayer.wantsPowerups() ? "Yes" : "No")));
                eb.next(ChatColor.GREEN + "Powerup: " + ChatColor.WHITE + StringUtil.niceString(spleggPlayer.getPowerupType().toString().replaceAll("_", " ")));
                eb.next(ChatColor.GREEN + "Trail: " + ChatColor.WHITE + StringUtil.niceString(spleggPlayer.getTrailType().toString().replaceAll("_", " ")));
                eb.next(ChatColor.GREEN + "Gamemode: " + ChatColor.WHITE + (spleggPlayer.hasVotedGameMode() ? StringUtil.niceString(spleggPlayer.getVotedGameMode().toString().replaceAll("_", " ")) : StringUtil.niceString(SpleggGameMode.STANDARD.toString())));
                eb.blank();
                eb.next(ChatColor.YELLOW + "Powerup Vote: " + getPowerupVotes());
                eb.next(ChatColor.YELLOW + "Gamemode Vote: " + ChatColor.WHITE + getCurrentGameMode());
                eb.next(ChatColor.YELLOW + "Map Votes");

                for (BaseMap map : Main.getInstance().mapManager.getMaps())
                    eb.next(ChatColor.WHITE + map.getName() + " " + ChatColor.GREEN + "(" + Main.getInstance().gameManager.voteManager.getVotes(map) + ")");

                return eb.build();
            }
        });
    }

    private String getPowerupVotes() {
        int yes = 0, no = 0;
        for(SpleggPlayer spleggPlayer : Main.getInstance().spleggPlayerManager.toCollection()){
            if(!spleggPlayer.hasVotedEnablePowerups())
                continue;

            if(spleggPlayer.wantsPowerups())
                yes++;
            else
                no++;
        }

        return ChatColor.DARK_GREEN + "" + yes + ChatColor.WHITE + "/" + ChatColor.DARK_RED + "" + no;
    }

    private String getCurrentGameMode() {
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

        return StringUtil.niceString(gameMode.toString().replaceAll("_", " "));
    }

}
