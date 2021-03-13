package net.royalbyte.creative;

import net.royalbyte.creative.commands.Command_plot;
import net.royalbyte.creative.database.MySQL;
import net.royalbyte.creative.handler.CreativeHandler;
import net.royalbyte.creative.handler.PlayerHandler;
import net.royalbyte.creative.listener.Listener_Build;
import net.royalbyte.creative.listener.Listener_Changeground;
import net.royalbyte.creative.listener.Listener_Join;
import net.royalbyte.creative.listener.Listener_Move;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class Creative extends JavaPlugin {

    private static Creative instance;
    public CreativeHandler creativeHandler;
    private String prefix, noperm, must_a_player, player_not_online;
    private MySQL mySQL;


    @Override
    public void onEnable() {
        instance = this;
        this.creativeHandler = new CreativeHandler(instance);

        this.mySQL = new MySQL(getCreativeHandler().getConfiguration().getString("host") ,getCreativeHandler().getConfiguration().getString("username"), getCreativeHandler().getConfiguration().getString("password"), getCreativeHandler().getConfiguration().getString("database"), getCreativeHandler().getConfiguration().getInt("port"));

        getCreativeHandler().createWorld();
        getCreativeHandler().registerPlotData();

        this.prefix = "§b§lCreative §8× §7";
        this.noperm = this.prefix + "§7Du bist nicht berechtigt dieses Kommando zu verwenden.";
        this.must_a_player = this.prefix + "§7Du musst ein §bSpieler §7sein!";
        this.player_not_online = this.prefix + "§7Der Angegebene Spieler konnte nicht gefunden werden.";

        Bukkit.getPluginManager().registerEvents(new Listener_Join(), this);
        Bukkit.getPluginManager().registerEvents(new Listener_Build(), this);
        Bukkit.getPluginManager().registerEvents(new Listener_Move(), this);
        Bukkit.getPluginManager().registerEvents(new Listener_Changeground(), this);

        getCommand("plot").setExecutor(new Command_plot());

        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            getCreativeHandler().getPlayerplot().put(onlinePlayer, getCreativeHandler().getPlotIDbyLocation(onlinePlayer.getLocation()));
        }

        new BukkitRunnable(){

            @Override
            public void run() {
                for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                    new PlayerHandler(onlinePlayer).sendScoreBoard();
                }
            }
        }.runTaskTimer(Creative.getInstance(), 0, 50*20);

    }

    @Override
    public void onDisable() {
        getMySQL().close();

    }


    public static Creative getInstance() {
        return instance;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getNoperm() {
        return noperm;
    }

    public String getMust_a_player() {
        return must_a_player;
    }

    public String getPlayer_not_online() {
        return player_not_online;
    }

    public MySQL getMySQL() {
        return mySQL;
    }

    public CreativeHandler getCreativeHandler() {
        return creativeHandler;
    }
}

