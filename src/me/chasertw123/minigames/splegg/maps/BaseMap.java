package me.chasertw123.minigames.splegg.maps;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.List;

/**
 * Created by Scott Hiett on 7/31/2017.
 */
public class BaseMap {

    private FileConfiguration configFile;
    private String name, description;
    private List<String> builders;

    public BaseMap(String map) {
        configFile = YamlConfiguration.loadConfiguration(new File("maps/" + map + "/config.yml"));

        this.name = configFile.getString("map.name");
        this.description = configFile.getString("map.description");
        this.builders = configFile.getStringList("map.builders");
    }

    public String getName() {
        return name;
    }

    public FileConfiguration getConfigFile() {
        return configFile;
    }

    public List<String> getBuilders() {
        return builders;
    }

    public String getDescription() {
        return description;
    }

}
