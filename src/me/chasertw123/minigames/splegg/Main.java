package me.chasertw123.minigames.splegg;

import me.chasertw123.minigames.core.api.v2.CoreAPI;
import me.chasertw123.minigames.shared.framework.ServerGameType;
import me.chasertw123.minigames.shared.framework.ServerType;
import me.chasertw123.minigames.splegg.events.EventManager;
import me.chasertw123.minigames.splegg.game.GameManager;
import me.chasertw123.minigames.splegg.maps.managers.MapManager;
import me.chasertw123.minigames.splegg.users.SpleggPlayerManager;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Scott Hiett on 8/7/2017.
 */
public class Main extends JavaPlugin {

    public static final String PREFIX = ChatColor.YELLOW + "" + ChatColor.BOLD + "Splegg" + ChatColor.DARK_GRAY + " > " + ChatColor.RESET;

    public SpleggPlayerManager spleggPlayerManager;
    public MapManager mapManager;
    public GameManager gameManager;

    @Override
    public void onLoad() {
        CoreAPI.getServerDataManager().setServerType(ServerType.MINIGAME);
        CoreAPI.getServerDataManager().setServerGameType(ServerGameType.SPLEGG);
    }

    @Override
    public void onEnable() {
        instance = this;

        spleggPlayerManager = new SpleggPlayerManager();
        mapManager = new MapManager();
        gameManager = new GameManager();

        new EventManager();
    }

    @Override
    public void onDisable() {
        instance = null;
    }

    // Static instance

    private static Main instance;

    public static Main getInstance() {
        return instance;
    }

}
