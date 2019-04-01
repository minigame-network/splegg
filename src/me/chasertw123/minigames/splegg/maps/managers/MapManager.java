package me.chasertw123.minigames.splegg.maps.managers;

import me.chasertw123.minigames.splegg.Main;
import me.chasertw123.minigames.splegg.maps.GameMap;
import me.chasertw123.minigames.splegg.maps.BaseMap;
import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.World;
import org.bukkit.WorldCreator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Scott Hiett on 8/7/2017.
 */
public class MapManager {

    private List<BaseMap> maps;
    private World lobby = null, game = null;
    private Random random = new Random();

    public MapManager() {
        maps = new ArrayList<>();

        try {
            if (new File("game").exists())
                FileUtils.deleteDirectory(new File("game"));

            if (!new File("lobby").exists())
                FileUtils.copyDirectory(new File("maps/lobby"), new File("lobby"));

            lobby = Bukkit.getServer().createWorld(new WorldCreator("lobby"));

            lobby.setDifficulty(Difficulty.PEACEFUL);
            lobby.setGameRuleValue("doMobSpawning", "false");
            lobby.setGameRuleValue("doDaylightCycle", "false");
            lobby.setTime(6000L);

            File[] mapList = new File("maps").listFiles(File::isDirectory);

            List<String> possibleMaps = new ArrayList<>();

            for (File file : mapList)
                if (file.isDirectory() && !file.getName().equalsIgnoreCase("lobby"))
                    possibleMaps.add(file.getName());

            if (possibleMaps.size() == 0) {
                System.err.println("ERROR! There are NO maps loaded!");
                Bukkit.shutdown();
            } else if (possibleMaps.size() <= 3)
                for (String s : possibleMaps)
                    maps.add(new BaseMap(s));

            else
                for (int i = 0; i < 3; i++) {
                    String map = selectRandomMap(possibleMaps);
                    possibleMaps.remove(map);
                    maps.add(new BaseMap(map));
                }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String selectRandomMap(List<String> toSelect) {
        int id = random.nextInt(toSelect.size());
        String map = toSelect.get(id);
        return map;
    }

    public List<BaseMap> getMaps() {
        return maps;
    }

    public World getLobbyWorld() {
        return lobby;
    }

    public World getGameWorld() {
        return game;
    }

    public void loadMap(String gameMap) {
        try {
            FileUtils.copyDirectory(new File("maps/" + gameMap), new File("game"));

            Bukkit.getServer().createWorld(new WorldCreator("game"));
            game = Bukkit.getWorld("game");

            game.setDifficulty(Difficulty.EASY);
            game.setGameRuleValue("doMobSpawning", "false");
            game.setGameRuleValue("doDaylightCycle", "false");
            game.setTime(6000L);

            Main.getInstance().gameManager.setGameMap(new GameMap(gameMap));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
