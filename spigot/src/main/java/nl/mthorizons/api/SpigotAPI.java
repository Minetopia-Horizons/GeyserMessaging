package nl.mthorizons.api;

import nl.mthorizons.SpigotPlugin;
import nl.mthorizons.codecs.PacketCodec;
import nl.mthorizons.packets.PayloadPacket;
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

}
