package nl.mthorizons.messaging.api;

import nl.mthorizons.messaging.common.packets.PayloadPacket;
import org.bukkit.entity.Player;

public class SpigotAPI {

    public static SpigotAPI api() {
        return null;
    }

    public boolean sendPacket(PayloadPacket packet) {
        return false;
    }

    public boolean sendPacket(Player player, PayloadPacket packet) { return false; }

}