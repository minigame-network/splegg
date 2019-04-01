package me.chasertw123.minigames.splegg.users;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Chase on 2/18/2017.
 */
public class SpleggPlayerManager {

    private Map<UUID, SpleggPlayer> users = new HashMap<>();

    public SpleggPlayer get(UUID uuid) {
        return users.get(uuid);
    }

    public boolean has(UUID uuid) {
        return users.containsKey(uuid);
    }

    public void add(SpleggPlayer spleggPlayer) {
        users.put(spleggPlayer.getCoreUser().getUUID(), spleggPlayer);
    }

    public Collection<SpleggPlayer> toCollection() {
        return users.values();
    }

    public void remove(UUID uuid) {
        users.remove(uuid);
    }

}
