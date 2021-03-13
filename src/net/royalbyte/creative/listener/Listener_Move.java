package net.royalbyte.creative.listener;

import net.royalbyte.creative.Creative;
import net.royalbyte.creative.handler.CreativeHandler;
import net.royalbyte.creative.handler.PlayerHandler;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import javax.swing.*;
import java.awt.*;

public class Listener_Move implements Listener {

    @EventHandler
    public void onMove(final PlayerMoveEvent event) {
        if (event.getTo().distanceSquared(event.getFrom()) == 0) return;

        CreativeHandler creativeHandler = Creative.getInstance().getCreativeHandler();
        int newPlot = creativeHandler.getPlotIDbyLocation(event.getTo());

        if (!creativeHandler.getPlayerplot().containsKey(event.getPlayer()))
            creativeHandler.getPlayerplot().put(event.getPlayer(), newPlot);

        if (creativeHandler.getPlayerplot().get(event.getPlayer()) != newPlot) {
            creativeHandler.getPlayerplot().put(event.getPlayer(), newPlot);
            if (newPlot != -1) {
                event.getPlayer().sendTitle("§7#" + newPlot, "§b" + creativeHandler.getPlotdata().get(newPlot).getOwner_name());
                new PlayerHandler(event.getPlayer()).sendScoreBoard();
            }else new PlayerHandler(event.getPlayer()).sendScoreBoard();
        }
    }

}
