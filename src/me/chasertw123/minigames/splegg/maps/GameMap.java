package me.chasertw123.minigames.splegg.maps;

import me.chasertw123.minigames.splegg.Main;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Scott Hiett on 8/7/2017.
 */
public class GameMap extends BaseMap {

    private List<Location> spawns;

    public GameMap(String map) {
        super(map);

        spawns = getLocationList(getConfigFile().getStringList("spawns"));
    }

    public List<Location> getSpawns() {
        return spawns;
    }

    private List<Location> getLocationList(List<String> values) {
        List<Location> data = new ArrayList<>();

        for (String s : values)
            data.add(getLocation(s));

        return data;
    }

    private Location getLocation(String target) {
        String[] parts = target.split(",");

        return new Location(Main.getInstance().mapManager.getGameWorld(), Integer.parseInt(parts[0].replaceAll(" ", "")),
                Integer.parseInt(parts[1].replaceAll(" ", "")), Integer.parseInt(parts[2].replaceAll(" ", "")));
    }

}
