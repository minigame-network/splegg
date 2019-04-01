package me.chasertw123.minigames.splegg.game.loops;

import me.chasertw123.minigames.splegg.Main;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Created by Chase on 7/30/2017.
 */
public abstract class GameLoop extends BukkitRunnable {

    public int interval;

    public GameLoop(int interval, long tickRate) {
        this.interval = interval;
        this.runTaskTimer(Main.getInstance(), 0L, tickRate);
    }

    public GameLoop(int interval, long startDelay, long tickRate) {
        this.interval = interval;
        this.runTaskTimer(Main.getInstance(), startDelay, tickRate);
    }
}
