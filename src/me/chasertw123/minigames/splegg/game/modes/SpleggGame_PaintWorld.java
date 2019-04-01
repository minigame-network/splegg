package me.chasertw123.minigames.splegg.game.modes;

import me.chasertw123.minigames.splegg.Main;
import me.chasertw123.minigames.splegg.utils.LocationUtils;
import me.chasertw123.minigames.splegg.users.SpleggPlayer;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

/**
 * Created by ScottsCoding on 12/08/2017.
 */
public class SpleggGame_PaintWorld extends AbstractSpleggGameMode {

    private List<UUID> red = new ArrayList<>();
    private List<Location> hitBlockList = new ArrayList<>(), possibleBlocks = new ArrayList<>();
    private Random random = new Random();

    public SpleggGame_PaintWorld() {
        super("Paint World", "In this gamemode, you have to cover the map in your team's color. " +
                "The more blocks you cover, the more chance you have of winning!", new ItemStack(Material.WOOL),
                3);
    }

    @Override
    public void onShoot(SpleggPlayer shooter) {

    }

    @Override
    public void onTweetsBlockRemove(Block block) {
        block.setType(Material.WOOL);
        block.setData((byte) 0);
    }

    @Override
    public void onEggLand(Location hitBlock, SpleggPlayer shooter) {
        boolean found = false;

        for(Location l : possibleBlocks)
            if(LocationUtils.locationBlockLocationsMatch(l, hitBlock))
                found = true;

        if(!found)
            return;

        hitBlock.getBlock().setType(Material.WOOL);

        if(shooter != null)
            hitBlock.getBlock().setData(getTeam(shooter).getColorByte());

        if(!hitBlockListContains(hitBlock))
            hitBlockList.add(hitBlock);
    }

    @Override
    public void onGameStart() {
        //Scan the map for available blocks.
        Chunk[] chunks = Main.getInstance().mapManager.getGameWorld().getLoadedChunks();
        for (Chunk chunk : chunks) {
            World world = chunk.getWorld();
            for (int x = 0; x < 17; x++) {
                for (int y = 0; y < (world.getMaxHeight() - 1); y++) {
                    for (int z = 0; z < 17; z++) {
                        Block block = chunk.getBlock(x, y, z);

                        if(block.getType() != Material.AIR) {
                            possibleBlocks.add(block.getLocation());
                        }
                    }
                }
            }
        }

        //Assign teams
        int redCount = Bukkit.getServer().getOnlinePlayers().size() / 2;

        List<Player> onlinePlayers = new ArrayList<>();
        onlinePlayers.addAll(Bukkit.getServer().getOnlinePlayers());

        for(int i = 0; i < redCount; i++) {
            //select a random player
            Player selected = onlinePlayers.get(random.nextInt(onlinePlayers.size()));
            onlinePlayers.remove(selected);
            red.add(selected.getUniqueId());
            selected.sendMessage(Main.PREFIX + "You are on team " + ChatColor.RED + "" + ChatColor.BOLD + "RED" + ChatColor.RESET + "!");
        }

        for(Player p : onlinePlayers)
            p.sendMessage(Main.PREFIX + "You are on team " + ChatColor.BLUE + "" + ChatColor.BOLD + "BLUE" + ChatColor.RESET + "!");
    }

    @Override
    public void onGameEnd(GameEndReason reason) {
        //Calculate all of the blocks!
        int red = 0, blue = 0;

        for(Location l : possibleBlocks) {
            if(l.getBlock().getType() != Material.WOOL)
                continue;

            if(l.getBlock().getData() == SpleggTeam.RED.getColorByte())
                red++;
            else if(l.getBlock().getData() == SpleggTeam.BLUE.getColorByte())
                blue++;
        }

        for(Player player : Bukkit.getServer().getOnlinePlayers()) {
            SpleggPlayer spleggPlayer = Main.getInstance().spleggPlayerManager.get(player.getUniqueId());

            spleggPlayer.updatePlaytime();

            spleggPlayer.getCoreUser().sendCenteredMessage(ChatColor.RED + "" + ChatColor.STRIKETHROUGH + "---------------------");
            spleggPlayer.getCoreUser().sendCenteredMessage(" ");

            if (red == blue) {
                spleggPlayer.getCoreUser().sendCenteredMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "The game has ended in a draw!");

                spleggPlayer.getCoreUser().addActivity("Draw in Splegg (PW)");
            } else {
                SpleggTeam winningTeam = (red > blue ? SpleggTeam.RED : SpleggTeam.BLUE);

                if(getTeam(spleggPlayer) == winningTeam)
                    spleggPlayer.getCoreUser().addActivity("Win in Splegg (PW)");
                else
                    spleggPlayer.getCoreUser().addActivity("Loss in Splegg (PW)");

                spleggPlayer.getCoreUser().sendCenteredMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "The winning team is " + winningTeam.toString().toUpperCase() + "!");
            }

            spleggPlayer.getCoreUser().sendCenteredMessage(ChatColor.RED + "RED covered " + red + " blocks!");
            spleggPlayer.getCoreUser().sendCenteredMessage(ChatColor.BLUE + "BLUE covered " + blue + " blocks!");
            spleggPlayer.getCoreUser().sendCenteredMessage(" ");
            spleggPlayer.getCoreUser().sendCenteredMessage(ChatColor.RED + "" + ChatColor.STRIKETHROUGH + "---------------------");
        }
    }

    @Override
    public void onTNTExplode(List<Block> affectedBlocks) {
        //TODO: Get the person who set off the TNT.
        for(Block b : affectedBlocks) {
            b.setType(Material.WOOL);
            b.setData((byte) 0);
        }
    }

    @Override
    public void onPlayerDeath(SpleggPlayer spleggPlayer) {
        spleggPlayer.sendPrefixedMessage(ChatColor.LIGHT_PURPLE + "Poof! Back to the top.");
        spleggPlayer.getPlayer().teleport(Main.getInstance().gameManager.getGameMap().getSpawns().get(0));
    }

    public SpleggTeam getTeam(SpleggPlayer player) {
        return red.contains(player.getUuid()) ? SpleggTeam.RED : SpleggTeam.BLUE;
    }

    private boolean hitBlockListContains(Location location) {
        for(Location l : hitBlockList)
            if(LocationUtils.locationBlockLocationsMatch(location, l))
                return true;

        return false;
    }

    public enum SpleggTeam {

        RED (14), BLUE (11);

        private int colorCode;

        SpleggTeam (int colorCode) {
            this.colorCode = colorCode;
        }

        public int getColorCode() {
            return colorCode;
        }

        public byte getColorByte(){
            return (byte) getColorCode();
        }

    }

}
