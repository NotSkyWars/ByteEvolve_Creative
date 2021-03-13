package net.royalbyte.creative.handler;

import net.minecraft.server.v1_8_R3.*;
import net.royalbyte.creative.Creative;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PlayerHandler {


    private Player player;

    public PlayerHandler(Player player) {
        this.player = player;
    }

    public void sendScoreBoard() {
        Scoreboard sb = new Scoreboard();
        ScoreboardObjective obj = sb.registerObjective("§bCreative", IScoreboardCriteria.b);
        PacketPlayOutScoreboardObjective createpacket = new PacketPlayOutScoreboardObjective(obj, 0);
        PacketPlayOutScoreboardDisplayObjective display = new PacketPlayOutScoreboardDisplayObjective(1, obj);
        obj.setDisplayName("LOBBY");

        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        String plot ="";

        CreativeHandler creativeHandler = Creative.getInstance().getCreativeHandler();

        if(!creativeHandler.getPlayerplot().containsKey(player)) plot = "Road";
        if (creativeHandler.getPlayerplot().get(player) == -1) plot = "Road";
        else plot = creativeHandler.getPlotdata().get(creativeHandler.getPlayerplot().get(player)).getOwner_name();

        ScoreboardScore a = new ScoreboardScore(sb, obj, "§8§M§l------------" + "§2");
        ScoreboardScore a2 = new ScoreboardScore(sb, obj, "§6✦ §8▎ §7Profil");
        ScoreboardScore a3 = new ScoreboardScore(sb, obj, " §8➥ §7" + player.getName() + "§a");
        ScoreboardScore a4 = new ScoreboardScore(sb, obj, "§8  ");
        ScoreboardScore a8 = new ScoreboardScore(sb, obj, "§c✪ §8▎ §7Plot");
        ScoreboardScore a9 = new ScoreboardScore(sb, obj, " §8➥ §7" + plot);
        ScoreboardScore a10 = new ScoreboardScore(sb, obj, " " + "§d");
        ScoreboardScore a11 = new ScoreboardScore(sb, obj, "§c❤ §8▎ §7Uhrzeit");
        ScoreboardScore a12 = new ScoreboardScore(sb, obj, " §8➥ §7" + formatter.format(date));
        ScoreboardScore a19 = new ScoreboardScore(sb, obj, "§8§M§l------------" + "§3");

        a.setScore(17);
        a2.setScore(16);
        a3.setScore(15);
        a4.setScore(14);
        a8.setScore(10);
        a9.setScore(9);
        a10.setScore(8);
        a11.setScore(7);
        a12.setScore(6);
        a19.setScore(5);

        PacketPlayOutScoreboardObjective removePacket = new PacketPlayOutScoreboardObjective(obj, 1);
        PacketPlayOutScoreboardScore pa1 = new PacketPlayOutScoreboardScore(a);
        PacketPlayOutScoreboardScore pa2 = new PacketPlayOutScoreboardScore(a2);
        PacketPlayOutScoreboardScore pa3 = new PacketPlayOutScoreboardScore(a3);
        PacketPlayOutScoreboardScore pa4 = new PacketPlayOutScoreboardScore(a4);
        PacketPlayOutScoreboardScore pa8 = new PacketPlayOutScoreboardScore(a8);
        PacketPlayOutScoreboardScore pa9 = new PacketPlayOutScoreboardScore(a9);
        PacketPlayOutScoreboardScore pa10 = new PacketPlayOutScoreboardScore(a10);
        PacketPlayOutScoreboardScore pa11 = new PacketPlayOutScoreboardScore(a11);
        PacketPlayOutScoreboardScore pa12 = new PacketPlayOutScoreboardScore(a12);
        PacketPlayOutScoreboardScore pa19 = new PacketPlayOutScoreboardScore(a19);

        sendPacket(removePacket);
        sendPacket(createpacket);
        sendPacket(display);
        sendPacket(pa1);
        sendPacket(pa2);
        sendPacket(pa3);
        sendPacket(pa4);
        sendPacket(pa8);
        sendPacket(pa9);
        sendPacket(pa10);
        sendPacket(pa11);
        sendPacket(pa12);
        sendPacket(pa19);

    }
    public void sendPacket(Packet<?> packet) {
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
    }

}
