package net.royalbyte.creative.logger.enums;

public enum LogTypes {

    INFO("§7[§fINFO§7]"),
    WARNING("§7[§cWARNING§7]"),
    ERROR("§7[§4ERROR§7]"),
    SUCCESS("§7[§aSUCCESS§7]");

    private String prefix;

    public String getPrefix() {
        return prefix;
    }

    LogTypes(String prefix) {
        this.prefix = prefix;
    }
}
