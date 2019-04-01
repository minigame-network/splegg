package me.chasertw123.minigames.splegg.game;

import me.chasertw123.minigames.core.api.v2.CoreAPI;
import me.chasertw123.minigames.core.user.data.achievements.Achievement;
import me.chasertw123.minigames.core.user.data.stats.Stat;
import me.chasertw123.minigames.shared.framework.GeneralServerStatus;
import me.chasertw123.minigames.splegg.Main;
import me.chasertw123.minigames.splegg.game.loops.Loop_EggLauncher;
import me.chasertw123.minigames.splegg.game.loops.Loop_Game;
import me.chasertw123.minigames.splegg.game.loops.Loop_Lobby;
import me.chasertw123.minigames.splegg.game.modes.GameEndReason;
import me.chasertw123.minigames.splegg.game.modes.SpleggGameMode;
import me.chasertw123.minigames.splegg.game.states.GameState;
import me.chasertw123.minigames.splegg.maps.GameMap;
import me.chasertw123.minigames.splegg.maps.managers.VoteManager;
import me.chasertw123.minigames.splegg.users.SpleggPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Scott Hiett on 8/7/2017.
 */
public class GameManager {

    public static final int MIN_PLAYERS = 2, MAX_PLAYERS = 16;

    private boolean powerupsEnabled;

    public VoteManager voteManager;
    public GameMap gameMap;
    public Loop_Lobby lobbyLoop = new Loop_Lobby();
    public Loop_Game gameLoop = null;
    public SpleggGameMode currentGameMode = null;
    private long gameStartTime = 0;

    private GameState gameState;

    public GameManager() {
        powerupsEnabled = new Random().nextBoolean();
        gameState = GameState.LOBBY;
        voteManager = new VoteManager();

        CoreAPI.getServerDataManager().updateServerState(GeneralServerStatus.LOBBY, MAX_PLAYERS);
    }

    public long getGameStartTime() {
        return gameStartTime;
    }

    public void setCurrentGameMode(SpleggGameMode gameMode) {
        this.currentGameMode = gameMode;
    }

    public boolean isPowerupsEnabled() {
        return powerupsEnabled;
    }

    public void setPowerupsEnabled(boolean powerupsEnabled) {
        this.powerupsEnabled = powerupsEnabled;
    }

    public void setGameMap(GameMap gameMap) {
        this.gameMap = gameMap;
    }

    public GameState getGameState() {
        return gameState;
    }

    public GameMap getGameMap() {
        return gameMap;
    }

    public List<SpleggPlayer> getAliveSpleggPlayers() {
        List<SpleggPlayer> alive = new ArrayList<>();
        for (SpleggPlayer spleggPlayer : Main.getInstance().spleggPlayerManager.toCollection())
            if (spleggPlayer.isAlive())
                alive.add(spleggPlayer);

        return alive;
    }

    public List<SpleggPlayer> getDeadSpleggPlayers() {
        List<SpleggPlayer> dead = new ArrayList<>();
        for (SpleggPlayer spleggPlayer : Main.getInstance().spleggPlayerManager.toCollection())
            if (spleggPlayer.isDead())
                dead.add(spleggPlayer);

        return dead;
    }

    public void checkGame() {
        if (Main.getInstance().gameManager.getGameState() != GameState.GAME)
            return;

        if (getAliveSpleggPlayers().size() == 1) {
            endGame(GameEndReason.SOMEONE_WON);

        } else if (getAliveSpleggPlayers().size() == 0) {
            Bukkit.broadcastMessage(Main.PREFIX + "Something has gone wrong. There are no alive users and the game hasn't ended.");

            endGame(GameEndReason.ERROR);
        }
    }

    public void startGame() {

        gameState = GameState.GAME;
        gameStartTime = System.currentTimeMillis();
        currentGameMode.getGameModeClass().onGameStart();
        gameLoop = new Loop_Game(currentGameMode.getGameModeClass().getTime());

        CoreAPI.getServerDataManager().updateServerState(GeneralServerStatus.INGAME, MAX_PLAYERS);

        int count = 0;
        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            player.getInventory().clear();

            Main.getInstance().spleggPlayerManager.get(player.getUniqueId()).getCoreUser()
                    .incrementStat(Stat.SPLEGG_GAMES_PLAYED);

            //Don't add splegg launcher yet, will after 10 seconds.
            player.teleport(getGameMap().getSpawns().get(count));
            player.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 15, 2, true, true)); // Speed for the countdown

            count++;
        }

        new Loop_EggLauncher();
    }

    public void endGame(GameEndReason reason) {
        currentGameMode.getGameModeClass().onGameEnd(reason);
        gameState = GameState.ENDING;

        int highest = 0;
        SpleggPlayer highestP = null;

        for(SpleggPlayer spleggPlayer : Main.getInstance().spleggPlayerManager.toCollection()) {
            if (spleggPlayer.getEggsShot() > highest) {
                highest = spleggPlayer.getEggsShot();
                highestP = spleggPlayer;
            }
        }

        if(highestP != null)
            highestP.getCoreUser().unlockAchievement(Achievement.SPLEGG_TRIGGER);

        int highestBlocks = 0;
        SpleggPlayer highestB = null;

        for(SpleggPlayer spleggPlayer : Main.getInstance().spleggPlayerManager.toCollection()) {
            if (spleggPlayer.getBlocksBroken() > highestBlocks) {
                highestBlocks = spleggPlayer.getBlocksBroken();
                highestB = spleggPlayer;
            }
        }

        if(highestB != null)
            highestB.getCoreUser().unlockAchievement(Achievement.SPLEGG_BLOCK);

        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> {
            for (Player player : Bukkit.getServer().getOnlinePlayers())
                CoreAPI.getUser(player).sendToServer("hub");
        }, 20 * 15);

        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> {

            // DYNAMIC SERVER SYSTEM CODE WOOHOO
            CoreAPI.getServerDataManager().updateServerState(GeneralServerStatus.DELETE, MAX_PLAYERS);
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "stop");

        }, 20 * 25);
    }

}
