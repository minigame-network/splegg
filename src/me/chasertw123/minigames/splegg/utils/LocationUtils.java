package me.chasertw123.minigames.splegg.utils;

import org.bukkit.Location;

public class LocationUtils {

    public static boolean locationBlockLocationsMatch(Location a, Location b) {
        return a.getBlockX() == b.getBlockX() && a.getBlockY() == b.getBlockY() && a.getBlockZ() == b.getBlockZ();
    }

}
