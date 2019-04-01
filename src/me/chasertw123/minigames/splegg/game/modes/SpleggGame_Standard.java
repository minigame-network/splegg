package me.chasertw123.minigames.splegg.game.modes;

import me.chasertw123.minigames.core.user.data.achievements.Achievement;
import me.chasertw123.minigames.core.utils.items.cItemStack;
import me.chasertw123.minigames.splegg.Main;
import me.chasertw123.minigames.splegg.game.guis.Gui_PlayerTeleporter;
import me.chasertw123.minigames.splegg.game.loops.Loop_EndGame;
import me.chasertw123.minigames.splegg.users.SpleggPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * Created by ScottsCoding on 12/08/2017.
 */
public class SpleggGame_Standard extends AbstractSpleggGameMode {

    public SpleggGame_Standard() {
        super("Classic Splegg", "Plain old Splegg. Fire eggs at each other in an attempt to knock the " +
                "other person off, while charging up your powerups for the next attack.", new ItemStack(Material.STONE),
                8);
    }

    @Override
    public void onShoot(SpleggPlayer shooter) {}

    @Override
    public void onEggLand(Location hitBlock, SpleggPlayer shooter) {

        if (hitBlock.getBlock().getType() == Material.TNT) {
            hitBlock.getWorld().createExplosion(hitBlock, 6.0F);
            shooter.getCoreUser().unlockAchievement(Achievement.SPLEGG_TNT);
        }

        hitBlock.getBlock().setType(Material.AIR);
    }

    @Override
    public void onGameStart() {}

    @Override
    public void onGameEnd(GameEndReason reason) {

        if(reason == GameEndReason.SOMEONE_WON)
            new Loop_EndGame();

    }

    @Override
    public void onTNTExplode(List<Block> affectedBlocks) {
        for(Block block : affectedBlocks)
            block.setType(Material.AIR);
    }

    @Override
    public void onTweetsBlockRemove(Block block) {
        block.setType(Material.AIR);
    }

    @Override
    public void onPlayerDeath(SpleggPlayer spleggPlayer) {
        Player p = spleggPlayer.getPlayer();

        spleggPlayer.setDead(true);

        for(SpleggPlayer player : Main.getInstance().spleggPlayerManager.toCollection())
            if(player.getCoreUser().getCurrentGui() instanceof Gui_PlayerTeleporter)
                player.getCoreUser().setCurrentGui(new Gui_PlayerTeleporter(player)); // Update player teleporters

        spleggPlayer.sendPrefixedMessage(ChatColor.RED + "You have been eliminated!");
        spleggPlayer.updatePlaytime();

        int place = Main.getInstance().gameManager.getAliveSpleggPlayers().size() + 1;
        spleggPlayer.getCoreUser().addActivity(ordinal(place) + " in Splegg (C)");

        for(SpleggPlayer s : Main.getInstance().spleggPlayerManager.toCollection())
            if(s.getPlayer().getUniqueId() != spleggPlayer.getPlayer().getUniqueId())
                s.sendPrefixedMessage(ChatColor.GOLD + "" + ChatColor.BOLD + spleggPlayer.getPlayer().getName()
                        + ChatColor.RESET + " has been " + ChatColor.YELLOW + "" + ChatColor.BOLD + "ELIMINATED" + ChatColor.RESET + "!");

        p.getInventory().clear();

        p.getInventory().setItem(0, new cItemStack(Material.COMPASS, ChatColor.LIGHT_PURPLE + "Player Teleporter" + ChatColor.GRAY + " (Right Click)"));
        p.getInventory().setItem(8, new cItemStack(Material.BED, ChatColor.RED + "Back To Hub" + ChatColor.GRAY + " (Right Click)"));
        p.getInventory().setItem(4, new cItemStack(Material.PAPER, ChatColor.GREEN + "Join Splegg Queue" + ChatColor.GRAY + " (Right Click)"));

        p.setPlayerListName(ChatColor.RED + "" + ChatColor.BOLD + "DEAD " + ChatColor.RESET + p.getName());

        p.setAllowFlight(true);
        p.setFlying(true);
        p.getLocation().getWorld().strikeLightningEffect(p.getLocation());

        for (Player player : Bukkit.getServer().getOnlinePlayers())
            if(player.getUniqueId() != p.getUniqueId())
                player.hidePlayer(p);

        p.teleport(Main.getInstance().gameManager.getGameMap().getSpawns().get(0));
    }

}
