package net.royalbyte.creative.listener;

import net.royalbyte.creative.Creative;
import net.royalbyte.creative.handler.CreativeHandler;
import net.royalbyte.creative.handler.CreativePlot;
import net.royalbyte.creative.handler.PlotData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;


public class Listener_Changeground implements Listener {


    @EventHandler
    public void onClick(final InventoryClickEvent event) {
        try {
            if(event.getClickedInventory().getName().equalsIgnoreCase("§bChangeGround")){
                event.setCancelled(true);

                Player player = (Player) event.getWhoClicked();
                CreativeHandler creativeHandler = Creative.getInstance().getCreativeHandler();
                if (creativeHandler.getPlayerplot().get(player) == -1)
                    player.sendMessage(Creative.getInstance().getPrefix() + "§7Du bist auf keinem Plot");
                else {
                    PlotData plotData = creativeHandler.getPlotdata().get(creativeHandler.getPlayerplot().get(player));
                    if(plotData.getOwner_uuid().equalsIgnoreCase(player.getUniqueId().toString())) {
                        CreativePlot creativePlot = new CreativePlot(creativeHandler.getPlayerplot().get(player));
                        creativePlot.setBuildArea(event.getCurrentItem().getTypeId());
                        player.closeInventory();
                    }else player.sendMessage(Creative.getInstance().getPrefix() + "§cDas ist nicht dein Plot.");
                }

            }
        } catch (Exception e) {

        }
    }

}
