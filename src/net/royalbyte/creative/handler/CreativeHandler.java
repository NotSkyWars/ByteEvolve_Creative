package net.royalbyte.creative.handler;

import gnu.trove.map.hash.THashMap;
import io.netty.handler.codec.http.HttpContentEncoder;
import net.royalbyte.creative.Creative;
import net.royalbyte.creative.world.ClearMap_Generator;
import org.bukkit.*;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class CreativeHandler {

    private Creative instance;
    private Map<Integer, PlotData> plotdata;
    private Map<Player, Integer> playerplot;
    private File file;
    private YamlConfiguration configuration;

    public CreativeHandler(Creative instance) {
        this.instance = instance;
        this.plotdata = new HashMap<>();
        this.playerplot = new HashMap<>();
        this.file = new File("plugins//Creative//mysql.yml");
        this.configuration = new YamlConfiguration().loadConfiguration(this.file);
        this.createFile();

    }

    public File getFile() {
        return file;
    }

    public YamlConfiguration getConfiguration() {
        return configuration;
    }

    private void createFile() {
        new File("plugins//Creative").mkdirs();
        if (!this.file.exists()) {
            try {
                this.file.createNewFile();

                this.configuration.set("host", "localhost");
                this.configuration.set("password", "Password");
                this.configuration.set("database", "Creative");
                this.configuration.set("username", "root");
                this.configuration.set("port", 3306);

                this.saveCFG();
            } catch (IOException e) {
            }
        }
    }

    private void saveCFG() {
        try {
            this.configuration.save(this.file);
        } catch (IOException e) {
        }
    }

    public int getPlotIDbyLocation(final Location location) {
        for (int key : getPlotdata().keySet()) {
            CreativePlot creativePlot = new CreativePlot(key);
            if (creativePlot.isLocatBuildPlace(location)) {
                return key;
            }
        }
        return -1;
    }

    public Map<Player, Integer> getPlayerplot() {
        return playerplot;
    }

    public Map<Integer, PlotData> getPlotdata() {
        return plotdata;
    }

    public void registerPlotData() {
        this.plotdata.clear();
        ResultSet resultSet = instance.getMySQL().getResult("SELECT * FROM CREATIVE_PLOTS");

        try {
            while (resultSet.next()) {
                this.plotdata.put(resultSet.getInt("ID"), new PlotData(resultSet.getInt("ID"), resultSet.getInt("FROM_X"), resultSet.getInt("FROM_Z"), resultSet.getInt("TO_X"), resultSet.getInt("TO_Z"), resultSet.getString("UUID"), resultSet.getString("NAME")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getLastID() {
        return getPlotdata().size();
    }

    public void createWorld() {
        WorldCreator wc = new WorldCreator("CREATIVE");
        wc.generateStructures(false);
        wc.generator(new ClearMap_Generator());
        Bukkit.getServer().createWorld(wc);
    }

    public Location getNextPlotLocation() {
        ResultSet resultSet = instance.getMySQL().getResult("SELECT * FROM CREATIVE_PLOTS ORDER BY ID DESC LIMIT 1");
        Location location;
        try {
            while (resultSet.next()) {
                int x = resultSet.getInt("FROM_X");
                int z = resultSet.getInt("FROM_Z");
                location = new Location(Bukkit.getWorld("CREATIVE"), x, 0, z);
                if (location.getBlockZ() >= 200000) {
                    return new Location(Bukkit.getWorld("CREATIVE"), x + 60, 0, 0);
                } else
                    return location.clone().add(0, 0, 60);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new Location(Bukkit.getWorld("CREATIVE"), 0, 0, 0);
    }

}
