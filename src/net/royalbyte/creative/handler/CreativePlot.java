package net.royalbyte.creative.handler;

import net.royalbyte.creative.Creative;
import net.royalbyte.creative.database.MySQL;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.material.MaterialData;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CreativePlot {

    private int ID;
    private Creative instance;
    private MySQL mySQL;

    public CreativePlot(int ID) {
        this.ID = ID;
        instance = Creative.getInstance();
        mySQL = instance.getMySQL();
    }

    public boolean isLocatBuildPlace(final Location location) {
        Location firstblock = getFirstBlockLocation();
        Double from_x = (double) firstblock.getBlockX() + 3;
        Double from_z = (double) firstblock.getBlockZ() + 3;
        Double to_z = (double) firstblock.add(59, 0, 59).getBlockZ() - 3;
        Double to_x = (double) firstblock.clone().add(0, 0, 59).getBlockX() - 3;

        Double x = location.getX();
        Double y = location.getY();
        Double z = location.getZ();

        if (location.getWorld().getName().equalsIgnoreCase("CREATIVE")) {
            if ((from_x < x) && (x < to_x)) {
                if ((0 < y) && (y < 255)) {
                    if ((from_z < z) && (z < to_z)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public Location getSpawnLocation() {
        Location location = getFirstBlockLocation();
        return location.clone().add(2, 2, 30);
    }

    public Location getFirstBlockLocation() {
        if (instance.getCreativeHandler().getPlotdata().containsKey(this.ID)) {
            PlotData plotData = instance.getCreativeHandler().getPlotdata().get(this.ID);
            int x = plotData.getFROM_X();
            int z = plotData.getFROM_Z();
            return new Location(Bukkit.getWorld("CREATIVE"), x, 0, z);
        } else return null;
    }

    public void setBuildArea(final int material) {
        Location firstblock = getFirstBlockLocation();


        new BukkitRunnable() {
            int from_x = firstblock.getBlockX() + 4;
            int from_z = firstblock.getBlockZ() + 4;
            Location location = new Location(Bukkit.getWorld("CREATIVE"), from_x, 0, from_z);
            int z = 0;

            @Override
            public void run() {
                for (int i = 0; i < 52; i++) {
                    location.clone().add(i, 0, z).getBlock().setTypeId(material);
                }
                z++;
                if (z == 52) {
                    cancel();
                }
            }
        }.runTaskTimer(Creative.getInstance(), 0, 1);
    }

    public boolean exits() {
        ResultSet resultSet = mySQL.getResult("SELECT * FROM CREATIVE_PLOTS WHERE ID='" + this.ID + "'");
        try {

            if (resultSet.next()) {
                return resultSet.getString("UUID") != null;
            }
        } catch (SQLException e) {
            return false;
        }
        return false;
    }
}
