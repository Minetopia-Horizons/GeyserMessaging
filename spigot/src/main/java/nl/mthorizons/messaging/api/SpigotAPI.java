package nl.mthorizons.messaging.api;

import nl.mthorizons.messaging.SpigotPlugin;
import nl.mthorizons.messaging.common.codecs.PacketCodec;
import nl.mthorizons.messaging.common.packets.PayloadPacket;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Optional;

public class SpigotAPI {

    public static SpigotAPI api() {
        return SpigotPlugin.getInstance().getApi();
    }

    public boolean sendPacket(PayloadPacket packet) {
        Optional<? extends Player> optional = Bukkit.getOnlinePlayers().stream().findAny();
        if (optional.isEmpty()) return false;

        Player player = optional.get();
        player.sendPluginMessage(SpigotPlugin.getInstance(), "geysermessaging:main", PacketCodec.encodePacket(packet));
        return true;
    }

    public boolean sendPacket(Player player, PayloadPacket packet) {
        player.sendPluginMessage(SpigotPlugin.getInstance(), "geysermessaging:main", PacketCodec.encodePacket(packet));
        return true;
    }

}
