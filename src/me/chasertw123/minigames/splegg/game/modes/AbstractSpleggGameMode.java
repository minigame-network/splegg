package me.chasertw123.minigames.splegg.game.modes;

import me.chasertw123.minigames.core.utils.items.cItemStack;
import me.chasertw123.minigames.splegg.users.SpleggPlayer;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * Created by ScottsCoding on 12/08/2017.
 */
public abstract class AbstractSpleggGameMode {

    private String name, description;
    private ItemStack item;
    private int time;

    public AbstractSpleggGameMode(String name, String description, ItemStack item, int time) {
        this.name = name;
        this.description = description;
        this.item = item;
        this.time = time;
    }

    public int getTime() {
        return time;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public ItemStack getItem() {
        return new cItemStack(item).setDisplayName(ChatColor.RESET + name).addFancyLore(description, ChatColor.YELLOW.toString());
    }

    protected String ordinal(int i) {
        String[] sufixes = new String[] { "th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th" };
        switch (i % 100) {
            case 11:
            case 12:
            case 13:
                return i + "th";
            default:
                return i + sufixes[i % 10];

        }

    }

    public abstract void onTweetsBlockRemove(Block block);

    public abstract void onShoot(SpleggPlayer shooter);

    public abstract void onEggLand(Location hitBlock, SpleggPlayer shooter);

    public abstract void onGameStart();

    public abstract void onGameEnd(GameEndReason reason);

    public abstract void onTNTExplode(List<Block> affectedBlocks);

    public abstract void onPlayerDeath(SpleggPlayer spleggPlayer);

}
