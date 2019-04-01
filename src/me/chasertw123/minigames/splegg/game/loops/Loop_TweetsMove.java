package me.chasertw123.minigames.splegg.game.loops;

import me.chasertw123.minigames.splegg.Main;
import me.chasertw123.minigames.splegg.utils.LocationUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ScottsCoding on 12/08/2017.
 */
public class Loop_TweetsMove extends GameLoop {

    public static boolean isTweetsAlive = false;
    public static Chicken tweets = null;

    private List<Location> blocksSteppedOn = new ArrayList<>();

    public Loop_TweetsMove() {
        super(20 * 20, 1);
    }

    @Override
    public void run() {
        if(!isLocationRegistered() && tweets.getLocation().clone().subtract(0, 1, 0).getBlock().getType() != Material.AIR)
            blocksSteppedOn.add(tweets.getLocation().clone().subtract(0, 1,0));

        List<Location> toRemove = new ArrayList<>();
        for(Location l : blocksSteppedOn)
            if(!LocationUtils.locationBlockLocationsMatch(l, tweets.getLocation().clone().subtract(0, 1, 0)))
                toRemove.add(l);

        for(Location l : toRemove) {
            //parse to correct gamemode
            Main.getInstance().gameManager.currentGameMode.getGameModeClass().onTweetsBlockRemove(l.getBlock());

            blocksSteppedOn.remove(l);
        }

        if(interval-- == 0) {
            //Lets give him a big one out ;)
            TNTPrimed tnt = tweets.getWorld().spawn(tweets.getLocation(), TNTPrimed.class);
            tnt.setFuseTicks(1);

            tweets.remove();
            isTweetsAlive = false;
            tweets = null;

            for(Player p : Bukkit.getServer().getOnlinePlayers())
                p.sendMessage(Main.PREFIX + ChatColor.RED + "Tweets the chicken has fallen!");

            this.cancel();
        }
    }

    private boolean isLocationRegistered() {
        for(Location l : blocksSteppedOn)
            if(LocationUtils.locationBlockLocationsMatch(l, tweets.getLocation().clone().subtract(0, 1, 0)))
                return true;

        return false;
    }

}
