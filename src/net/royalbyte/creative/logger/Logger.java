package net.royalbyte.creative.logger;

import net.royalbyte.creative.Creative;
import net.royalbyte.creative.logger.enums.LogTypes;
import org.bukkit.Bukkit;

public class Logger {

    public Logger(final LogTypes logType, final String msg) {
        Bukkit.getConsoleSender().sendMessage(Creative.getInstance().getPrefix() + logType.getPrefix() + " §f" + msg);
    }
}
