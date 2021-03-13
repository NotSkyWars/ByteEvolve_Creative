package net.royalbyte.creative.handler;

import net.royalbyte.creative.Creative;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlayerPlotHandler {


    private String name, uuid;
    private Creative instance;

    public PlayerPlotHandler(String name) {
        this.name = name;
        this.uuid = Bukkit.getOfflinePlayer(name).getUniqueId().toString();
        this.instance = Creative.getInstance();
    }

    public void addPlot() {
        Location from = instance.getCreativeHandler().getNextPlotLocation();
        instance.getMySQL().update("INSERT INTO CREATIVE_PLOTS (UUID, NAME, FROM_X, FROM_Z, TO_X, TO_Z) VALUES ('" + uuid + "', '" + name + "', '" + from.getBlockX() + "'," +
                "'" + from.getBlockZ() + "', '" + from.clone().add(60, 0, 0).getBlockX() + "', '" + from.clone().add(0, 0, 60).getBlockZ() + "')");

        instance.getCreativeHandler().getPlotdata().put(instance.getCreativeHandler().getLastID() + 1, new PlotData(instance.getCreativeHandler().getLastID() + 1, from.getBlockX(), from.getBlockZ(), from.clone().add(60, 0, 0).getBlockX(), from.clone().add(0, 0, 60).getBlockZ(), uuid, name));

        Location to = from.clone().add(60, 0, 60);

        int distance_x = to.getBlockX() - from.getBlockX();
        int distance_z = to.getBlockZ() - from.getBlockZ();

        if (Bukkit.getPlayer(name) != null) {
            Player player = Bukkit.getPlayer(name);
            player.sendMessage(Creative.getInstance().getPrefix() + "§7Dein §bPlot §7wird erstellt...");
            new BukkitRunnable() {
                int i = 0;

                int xarg = distance_x;
                int zarg = distance_z;
                int z = from.getBlockZ();
                int y = from.getBlockY();

                int count = 0;

                @Override
                public void run() {

                    for (int i = 0; i < xarg; i++) {
                        if (this.i == 0 || this.i == 59) {
                            from.clone().add(i, 0, this.i).getBlock().setType(Material.WOOD);
                        } else if (this.i == 56 || this.i == 3) {
                            if (i == 0 || i == 59) {
                                from.clone().add(i, 0, this.i).getBlock().setType(Material.WOOD);
                            } else if (i == 1 || i == 2 || i == 57 || i == 58) {
                                from.clone().add(i, 0, this.i).getBlock().setType(Material.WOOD);
                                from.clone().add(i, 0, this.i).getBlock().setData((byte) 1);
                            } else {
                                from.clone().add(i, 1, this.i).getBlock().setTypeId(44);
                                from.clone().add(i, 0, this.i).getBlock().setTypeId(43);
                            }
                        } else if (this.i == 1 || this.i == 2 || this.i == 57 || this.i == 58) {
                            if (i == 0 || i == 59) {
                                from.clone().add(i, 0, this.i).getBlock().setType(Material.WOOD);
                            } else {
                                from.clone().add(i, 0, this.i).getBlock().setType(Material.WOOD);
                                from.clone().add(i, 0, this.i).getBlock().setData((byte) 1);
                            }
                        } else {
                            if (i == 0 || i == 59) {
                                from.clone().add(i, 0, this.i).getBlock().setType(Material.WOOD);
                            } else if (i == 1 || i == 2 || i == 57 || i == 58) {
                                from.clone().add(i, 0, this.i).getBlock().setType(Material.WOOD);
                                from.clone().add(i, 0, this.i).getBlock().setData((byte) 1);
                            } else if (i == 3 || i == 56) {
                                from.clone().add(i, 1, this.i).getBlock().setTypeId(44);
                                from.clone().add(i, 0, this.i).getBlock().setTypeId(43);
                            } else {
                                from.clone().add(i, 0, this.i).getBlock().setType(Material.GRASS);
                            }
                        }
                        count++;
                    }
                    z++;
                    i++;
                    if (i == zarg) {
                        z = from.getBlockZ();
                        i = 0;
                        y++;
                        player.sendTitle("§7Dein §bPlot §7wurde §aerfolgreich ", "§7erstellt.");
                        count++;
                        player.teleport(from.clone().add(2, 2, 30));
                        player.sendMessage(Creative.getInstance().getPrefix() + "§7Du bist nun bei deinem Plot");
                        cancel();
                    }

                }
            }.runTaskTimer(Creative.getInstance(), 0, 1);

        } else System.out.println(Creative.getInstance().getPlayer_not_online());

    }

    public boolean isLocationatPlotBuildPlace(final Location location) {
        List<Integer> plots = getPlots();
        for (int i = 0; i < plots.size(); i++) {
            CreativePlot creativePlot = new CreativePlot(plots.get(i));
            if (creativePlot.isLocatBuildPlace(location)) return true;
        }
        return false;
    }


    public List<Integer> getPlots() {
        ResultSet resultSet = instance.getMySQL().getResult("SELECT * FROM CREATIVE_PLOTS WHERE UUID='" + uuid + "'");
        try {
            List<Integer> list = new ArrayList<>();
            while (resultSet.next()) {
                list.add(resultSet.getInt("ID"));
            }
            return list;
        } catch (SQLException e) {

        }
        return null;
    }

    public boolean hasPlot() {
        return getPlots().size() != 0;
    }
}
