package net.royalbyte.creative.commands;

import net.royalbyte.creative.Creative;
import net.royalbyte.creative.handler.CreativeHandler;
import net.royalbyte.creative.handler.CreativePlot;
import net.royalbyte.creative.handler.PlayerPlotHandler;
import net.royalbyte.creative.handler.PlotData;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftRabbit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class Command_plot implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

        if (commandSender instanceof Player) {
            if(args.length != 0) {
                Player player = (Player) commandSender;
                if (args[0].equalsIgnoreCase("create")) {
                    if (args.length == 1) {
                        PlayerPlotHandler playerPlotHandler = new PlayerPlotHandler(player.getName());
                        playerPlotHandler.addPlot();
                    } else sendHelp(commandSender);
                } else if (args[0].equalsIgnoreCase("home")) {
                    if (args.length == 1) {
                        PlayerPlotHandler playerPlotHandler = new PlayerPlotHandler(player.getName());
                        if (playerPlotHandler.hasPlot()) {
                            CreativePlot creativePlot = new CreativePlot(playerPlotHandler.getPlots().get(0));
                            player.teleport(creativePlot.getSpawnLocation());
                            player.sendMessage(Creative.getInstance().getPrefix() + "§7Du bist nun bei deinem §b" + 1 + ". Plot§7.");
                        } else
                            player.sendMessage(Creative.getInstance().getPrefix() + "§7Du hast leider §ckein §7Plot. §7(§b/plot create§7)");
                    } else if (args.length == 2) {
                        PlayerPlotHandler playerPlotHandler = new PlayerPlotHandler(player.getName());
                        if (playerPlotHandler.hasPlot()) {
                            try {
                                Integer id = Integer.parseInt(args[1]);
                                List<Integer> plots = playerPlotHandler.getPlots();
                                if (plots.contains(id)) {
                                    CreativePlot creativePlot = new CreativePlot(id);
                                    player.teleport(creativePlot.getSpawnLocation());
                                    player.sendMessage(Creative.getInstance().getPrefix() + "§7Du bist nun bei deinem §b" + id + ". Plot§7.");
                                } else
                                    player.sendMessage(Creative.getInstance().getPrefix() + "§7Das Plot mit der ID §c" + id + "§7 gibt es nicht.");
                            } catch (NumberFormatException e) {
                                player.sendMessage(Creative.getInstance().getPrefix() + "§7Bitte gebe eine gültige §cID §7an.");
                                return true;
                            }
                        } else
                            player.sendMessage(Creative.getInstance().getPrefix() + "§7Du hast leider §ckein §7Plot. §7(§b/plot create§7)");
                    } else sendHelp(commandSender);
                } else if (args[0].equalsIgnoreCase("teleport") || args[0].equalsIgnoreCase("tp")) {
                    if (args.length == 2) {
                        String target = args[1];
                        PlayerPlotHandler playerPlotHandler = new PlayerPlotHandler(target);
                        if (playerPlotHandler.hasPlot()) {
                            CreativePlot creativePlot = new CreativePlot(playerPlotHandler.getPlots().get(0));
                            player.teleport(creativePlot.getSpawnLocation());
                            player.sendMessage(Creative.getInstance().getPrefix() + "§7Du bist nun bei §9" + target + "'s §b" + 1 + ". Plot§7.");
                        } else
                            player.sendMessage(Creative.getInstance().getPrefix() + "§7Der Spieler §b" + target + "§7 hat §ckein §7Plot.");
                    } else if (args.length == 3) {
                        String target = args[1];
                        PlayerPlotHandler playerPlotHandler = new PlayerPlotHandler(target);
                        if (playerPlotHandler.hasPlot()) {
                            try {
                                Integer id = Integer.parseInt(args[2]);
                                List<Integer> plots = playerPlotHandler.getPlots();
                                if (plots.contains(id)) {
                                    CreativePlot creativePlot = new CreativePlot(id);
                                    player.teleport(creativePlot.getSpawnLocation());
                                    player.sendMessage(Creative.getInstance().getPrefix() + "§7Du bist nun bei §9" + target + "'s §b" + id + ". Plot§7.");
                                } else
                                    player.sendMessage(Creative.getInstance().getPrefix() + "§9" + target + " §7hat §ckein §7Plot mit der ID §c" + id + "§7.");
                            } catch (NumberFormatException e) {
                                player.sendMessage(Creative.getInstance().getPrefix() + "§7Bitte gebe eine gültige §cID §7an.");
                                return true;
                            }
                        } else
                            player.sendMessage(Creative.getInstance().getPrefix() + "§7Du hast leider §ckein §7Plot. §7(§b/plot create§7)");
                    } else sendHelp(commandSender);
                } else if (args[0].equalsIgnoreCase("info") || args[0].equalsIgnoreCase("i")) {
                    if (args.length == 1) {
                        CreativeHandler creativeHandler = Creative.getInstance().getCreativeHandler();
                        if (creativeHandler.getPlayerplot().get(player) == -1)
                            player.sendMessage(Creative.getInstance().getPrefix() + "§7Du bist auf keinem Plot");
                        else {
                            PlotData plotData = creativeHandler.getPlotdata().get(creativeHandler.getPlayerplot().get(player));
                            player.sendMessage("§m§l§7-------- §bPlot-Info §m§l§7--------");
                            player.sendMessage("§bID§7: " + plotData.getID());
                            player.sendMessage("§bBesitzer§7: " + plotData.getOwner_name());
                            player.sendMessage("§m§l§7-------- §bPlot-Info §m§l§7--------");
                        }

                    } else sendHelp(player);
                } else if (args[0].equalsIgnoreCase("changeground")) {
                    if (args.length == 1) {
                        CreativeHandler creativeHandler = Creative.getInstance().getCreativeHandler();
                        if (creativeHandler.getPlayerplot().get(player) == -1)
                            player.sendMessage(Creative.getInstance().getPrefix() + "§7Du bist auf keinem Plot");
                        else {
                            PlotData plotData = creativeHandler.getPlotdata().get(creativeHandler.getPlayerplot().get(player));
                            if (plotData.getOwner_uuid().equalsIgnoreCase(player.getUniqueId().toString())) {

                                Inventory inventory = Bukkit.createInventory(null, 3 * 9, "§bChangeGround");

                                inventory.addItem(new ItemStack(Material.STONE));
                                inventory.addItem(new ItemStack(Material.COBBLESTONE));
                                inventory.addItem(new ItemStack(Material.SANDSTONE));
                                inventory.addItem(new ItemStack(Material.WOOD));
                                inventory.addItem(new ItemStack(Material.BRICK));
                                inventory.addItem(new ItemStack(Material.PRISMARINE));
                                inventory.addItem(new ItemStack(Material.PACKED_ICE));
                                inventory.addItem(new ItemStack(Material.GRASS));
                                inventory.addItem(new ItemStack(Material.OBSIDIAN));
                                inventory.addItem(new ItemStack(Material.MYCEL));
                                inventory.addItem(new ItemStack(Material.QUARTZ_BLOCK));
                                inventory.addItem(new ItemStack(Material.QUARTZ_ORE));
                                inventory.addItem(new ItemStack(Material.GLASS));
                                inventory.addItem(new ItemStack(Material.RED_SANDSTONE));
                                inventory.addItem(new ItemStack(Material.STAINED_CLAY));
                                inventory.addItem(new ItemStack(Material.BARRIER));
                                inventory.addItem(new ItemStack(Material.SNOW_BLOCK));

                                player.openInventory(inventory);
                                player.sendMessage(Creative.getInstance().getPrefix() + "§7Wähle deinen Boden aus...");
                            } else
                                player.sendMessage(Creative.getInstance().getPrefix() + "§cDas ist nicht dein Plot.");
                        }
                    } else sendHelp(player);
                } else sendHelp(commandSender);
            }else sendHelp(commandSender);
        } else commandSender.sendMessage(Creative.getInstance().getMust_a_player());
        return true;
    }

    private void sendHelp(CommandSender commandSender) {
        commandSender.sendMessage(Creative.getInstance().getPrefix() + "§7/plot <§bcreate§7> : Erstelle dir ein §bPlot§7.");
        commandSender.sendMessage(Creative.getInstance().getPrefix() + "§7/plot <§bhome§7> <§bID§7> : Teleportiere dich zu deinem §bPlot§7.");
        commandSender.sendMessage(Creative.getInstance().getPrefix() + "§7/plot <§bteleport§7> <§bID§7> : Teleportiere dich zu einem §bPlot§7.");
        commandSender.sendMessage(Creative.getInstance().getPrefix() + "§7/plot <§binfo§7> : Bekomme Informationen über ein §bPlot§7.");
        commandSender.sendMessage(Creative.getInstance().getPrefix() + "§7/plot <§bchangeground§7> : Ändere den Boden deines §bPlots§7.");
    }
}
