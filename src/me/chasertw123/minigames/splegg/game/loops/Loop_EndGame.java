package me.chasertw123.minigames.splegg.game.loops;

import me.chasertw123.minigames.core.user.data.achievements.Achievement;
import me.chasertw123.minigames.core.user.data.stats.Stat;
import me.chasertw123.minigames.splegg.Main;
import me.chasertw123.minigames.splegg.users.SpleggPlayer;
import net.minecraft.server.v1_8_R3.EntityFireworks;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.Items;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

/**
 * Created by Scott Hiett on 8/11/2017.
 */
public class Loop_EndGame extends GameLoop {

    private Random rand = new Random();
    private int radius = 32;

    public Loop_EndGame() {
        super(1, 20);

        SpleggPlayer winner = Main.getInstance().gameManager.getAliveSpleggPlayers().get(0);
        winner.getCoreUser().addActivity("1st in Splegg (C)");
        winner.getCoreUser().incrementStat(Stat.SPLEGG_GAMES_WON);
        winner.updatePlaytime();

        if(winner.getEggsShot() == 0)
            winner.getCoreUser().unlockAchievement(Achievement.SPLEGG_GOD);

        Player p = winner.getPlayer();
        p.getInventory().clear();

        p.setAllowFlight(true);
        p.setFlying(true);

        for(Player player : Bukkit.getServer().getOnlinePlayers()) {
            if(player.getUniqueId() != winner.getUuid())
                player.teleport(winner.getPlayer());

            SpleggPlayer spleggPlayer = Main.getInstance().spleggPlayerManager.get(player.getUniqueId());

            spleggPlayer.getCoreUser().sendCenteredMessage(ChatColor.RED + "" + ChatColor.STRIKETHROUGH + "-----------------------------");
            spleggPlayer.getCoreUser().sendCenteredMessage(" ");
            spleggPlayer.getCoreUser().sendCenteredMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "The Game has " + ChatColor.GOLD + "" + ChatColor.BOLD + "ENDED!");
            spleggPlayer.getCoreUser().sendCenteredMessage(ChatColor.AQUA + "The winner is " + ChatColor.YELLOW + "" + ChatColor.BOLD + "" + winner.getPlayer().getName());
            spleggPlayer.getCoreUser().sendCenteredMessage(" ");
            spleggPlayer.getCoreUser().sendCenteredMessage(ChatColor.RED + "" + ChatColor.STRIKETHROUGH + "-----------------------------");

            for(SpleggPlayer sp : Main.getInstance().gameManager.getDeadSpleggPlayers())
                player.showPlayer(sp.getPlayer());
        }
    }

    @Override
    public void run() {

        for (Player player : Bukkit.getOnlinePlayers()) {

            Location center = player.getLocation(), newLoc;
            double angle = rand.nextDouble() * 360;
            double x = center.getX() + (rand.nextDouble() * radius * Math.cos(Math.toRadians(angle)));
            double z = center.getZ() + (rand.nextDouble() * radius * Math.sin(Math.toRadians(angle)));
            newLoc = new Location(center.getWorld(), x, center.getY() + 2, z);

            playFirework(newLoc);
        }

    }

    private void playFirework(Location l) {

        FireworkEffect.Builder fwB = FireworkEffect.builder();
        Random r = new Random();
        fwB.flicker(r.nextBoolean());
        fwB.trail(r.nextBoolean());
        fwB.with(FireworkEffect.Type.values()[r.nextInt(FireworkEffect.Type.values().length)]);
        fwB.withColor(Color.fromRGB(r.nextInt(256), r.nextInt(256), r.nextInt(256)));
        fwB.withFade(Color.fromRGB(r.nextInt(256), r.nextInt(256), r.nextInt(256)));
        FireworkEffect fe = fwB.build();

        ItemStack item = new ItemStack(Items.FIREWORKS);

        FireworkMeta meta = (FireworkMeta) CraftItemStack.asCraftMirror(item).getItemMeta();
        meta.addEffect(fe);

        CraftItemStack.setItemMeta(item, meta);

        double y = l.getY();
        new BukkitRunnable() {

            @Override
            public void run() {

                EntityFireworks entity = new EntityFireworks(((CraftWorld) l.getWorld()).getHandle(), l.getX(), l.getY(), l.getZ(), item) {
                    @Override
                    public void t_() {
                        this.world.broadcastEntityEffect(this, (byte)17);
                        die();
                    }
                };

                entity.setInvisible(true);
                ((CraftWorld) l.getWorld()).getHandle().addEntity(entity);
            }
        }.runTaskLater(Main.getInstance(), 2);
    }

}
