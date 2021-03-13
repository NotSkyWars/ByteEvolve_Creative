package net.royalbyte.creative.listener;

import net.royalbyte.creative.Creative;
import net.royalbyte.creative.handler.PlayerHandler;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;


public class Listener_Join implements Listener {

    @EventHandler
    public void onJoin(final PlayerJoinEvent event) {
        event.getPlayer().setGameMode(GameMode.CREATIVE);

        Creative.getInstance().getCreativeHandler().getPlayerplot().put(event.getPlayer(), Creative.getInstance().getCreativeHandler().getPlotIDbyLocation(event.getPlayer().getLocation()));

        new BukkitRunnable() {

            @Override
            public void run() {
                new PlayerHandler(event.getPlayer()).sendScoreBoard();

                if(event.getPlayer().getName().equals("RoyalByte")){

                    for(Player all : Bukkit.getOnlinePlayers()){
                        all.sendMessage(" ");
                        all.sendMessage(Creative.getInstance().getPrefix() +"§8§k---------------------------------");
                        all.sendMessage(Creative.getInstance().getPrefix() + "   §bDer PLUGIN-ENTWICKLER ist gejoint!");
                        all.sendMessage(Creative.getInstance().getPrefix() +"§8§k---------------------------------");
                        all.sendMessage(" ");
                    }
                }
            }
        }.runTaskLaterAsynchronously(Creative.getInstance(), 10);

    }
}
