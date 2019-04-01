package me.chasertw123.minigames.splegg.users;

import me.chasertw123.minigames.core.api.misc.Title;
import me.chasertw123.minigames.core.api.v2.CoreAPI;
import me.chasertw123.minigames.core.user.User;
import me.chasertw123.minigames.core.user.data.stats.Stat;
import me.chasertw123.minigames.splegg.Main;
import me.chasertw123.minigames.splegg.game.modes.SpleggGameMode;
import me.chasertw123.minigames.splegg.game.powerups.PowerupType;
import me.chasertw123.minigames.splegg.game.trails.TrailType;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * Created by Scott Hiett on 8/7/2017.
 */
public class SpleggPlayer {

    private UUID uuid;
    private String votedMap = null;
    private SpleggGameMode votedGameMode = null;
    private ShovelType shovelType = ShovelType.STONE;
    private boolean isDead = false, hasVotedEnablePowerups = false, voteEnablePowerups = true, powerupActivated = false, hasUpdatedPlaytime = false;
    private int points = 0, eggsShot = 0, blocksBroken = 0;
    private PowerupType powerupType = PowerupType.RAPID_FIRE;
    private TrailType trailType = TrailType.NONE;
    private long lastEggShootTime = 0;

    public SpleggPlayer(UUID uuid) {
        this.uuid = uuid;
    }

    public long getLastEggShootTime() {
        return lastEggShootTime;
    }

    public boolean canShoot() {

        if(lastEggShootTime + 160 < System.currentTimeMillis()) {

            setLastEggShootTime(System.currentTimeMillis());
            eggsShot++;
            return true;
        }

        return false;
    }

    public void updatePlaytime() {
        if(hasUpdatedPlaytime)
            return;

        hasUpdatedPlaytime = true;

        getCoreUser().incrementStat(Stat.SPLEGG_PLAYTIME, (int) ((System.currentTimeMillis() - Main.getInstance().gameManager.getGameStartTime()) / 1000));
    }

    public void incrementBlocksBroken(){
        blocksBroken++;
    }

    public int getBlocksBroken() {
        return blocksBroken;
    }

    public int getEggsShot() {
        return eggsShot;
    }

    public void setLastEggShootTime(long lastEggShootTime) {
        this.lastEggShootTime = lastEggShootTime;
    }

    public boolean hasVotedGameMode(){
        return votedGameMode != null;
    }

    public SpleggGameMode getVotedGameMode() {
        return votedGameMode;
    }

    public void setVotedGameMode(SpleggGameMode votedGameMode) {
        this.votedGameMode = votedGameMode;
    }

    public boolean hasVotedEnablePowerups() {
        return hasVotedEnablePowerups;
    }

    public boolean isPowerupActivated() {
        return powerupActivated;
    }

    public void setPowerupActivated(boolean powerupActivated) {
        this.powerupActivated = powerupActivated;
    }

    public int getPoints() {
        return points;
    }

    public TrailType getTrailType() {
        return trailType;
    }

    public void setTrailType(TrailType trailType) {
        this.trailType = trailType;
    }

    public void setPowerupType(PowerupType powerupType) {
        this.powerupType = powerupType;
    }

    public PowerupType getPowerupType() {
        return powerupType;
    }

    public void setPoints(int points) {
        this.points = points;
        updateXPBar();
    }

    public boolean wantsPowerups() {
        return voteEnablePowerups;
    }

    public void setWantsPowerups(boolean voteEnablePowerups) {
        this.voteEnablePowerups = voteEnablePowerups;
        this.hasVotedEnablePowerups = true;
    }

    public void addPoints(int amount){
        points += amount;

        if(points >= getPowerupType().getPowerupClass().getPointsToGet())
            Title.sendActionbar(getPlayer(), ChatColor.GOLD + "Powerup Ready!");

        updateXPBar();
    }

    public void updateXPBar() {
        int requiredForPowerup = getPowerupType().getPowerupClass().getPointsToGet();
        int currentPoints = getPoints();

        float count = (float) (currentPoints * 100) / requiredForPowerup;

        if (count > 100)
            count = 100;

        int intCount = (int) count;

        getPlayer().setLevel(intCount);

        getPlayer().setPlayerListName(ChatColor.YELLOW + "" + ChatColor.BOLD + intCount + "% " + ChatColor.RESET + getPlayer().getName());

        getPlayer().setLevel(0); // Each level has a different fill value so have to reset it!
        getPlayer().setExp(count / 100);
    }

    public boolean isDead() {
        return isDead;
    }

    public void setDead(boolean dead) {
        isDead = dead;
    }

    public boolean isAlive() {
        return !isDead();
    }

    public void sendPrefixedMessage(String message) {
        getPlayer().sendMessage(Main.PREFIX + message);
    }

    public ShovelType getShovelType() {
        return shovelType;
    }

    public void setShovelType(ShovelType shovelType) {
        this.shovelType = shovelType;
    }

    public boolean hasVoted() {
        return votedMap != null;
    }

    public String getVotedMap() {
        return votedMap;
    }

    public void setVotedMap(String votedMap) {
        this.votedMap = votedMap;
    }

    public UUID getUuid() {
        return uuid;
    }

    public Player getPlayer() {
        return Bukkit.getServer().getPlayer(uuid);
    }

    public User getCoreUser() {
        return CoreAPI.getUser(getPlayer());
    }

}
