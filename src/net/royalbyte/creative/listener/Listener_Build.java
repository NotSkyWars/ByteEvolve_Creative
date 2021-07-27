package net.royalbyte.creative.listener;

import net.minecraft.server.v1_8_R3.AchievementList;
import net.royalbyte.creative.Creative;
import net.royalbyte.creative.handler.CreativeHandler;
import net.royalbyte.creative.handler.CreativePlot;
import net.royalbyte.creative.handler.PlayerPlotHandler;
import net.royalbyte.creative.handler.PlotData;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import javax.swing.*;

public class Listener_Build implements Listener {

    @EventHandler
    public void onBuild(final BlockPlaceEvent event) {

        Location location = event.getBlock().getLocation();
        Player player = event.getPlayer();
        CreativeHandler creativeHandler = Creative.getInstance().getCreativeHandler();
        int plotid = creativeHandler.getPlotIDbyLocation(location);

        if(plotid == -1){
            if(!player.hasPermission("Creative.build")) {
                player.sendMessage(Creative.getInstance().getPrefix() + "§7Du bist auf keinem Plot");
                event.setCancelled(true);
            }
        }else {
            PlotData plotData = creativeHandler.getPlotdata().get(plotid);
            if(!(plotData.getOwner_uuid().equalsIgnoreCase(player.getUniqueId().toString()) || player.hasPermission("Creative.build"))){
                event.setCancelled(true);
                player.sendMessage(Creative.getInstance().getPrefix() + "§cHier kannst du nicht bauen.");
            }
        }
        System.out.println("Test");

    }


    @EventHandler
    public void onBreak(final BlockBreakEvent event) {

        Location location = event.getBlock().getLocation();
        Player player = event.getPlayer();
        CreativeHandler creativeHandler = Creative.getInstance().getCreativeHandler();
        int plotid = creativeHandler.getPlotIDbyLocation(location);

        if(plotid == -1){
            if(!player.hasPermission("Creative.build")) {
                player.sendMessage(Creative.getInstance().getPrefix() + "§7Du bist auf keinem Plot");
                event.setCancelled(true);
            }
        }else {
            PlotData plotData = creativeHandler.getPlotdata().get(plotid);
            if(!(plotData.getOwner_uuid().equalsIgnoreCase(player.getUniqueId().toString()) || player.hasPermission("Creative.build"))){
                event.setCancelled(true);
                player.sendMessage(Creative.getInstance().getPrefix() + "§cHier kannst du nicht bauen.");
            }
        }

    }
}
