package nl.mthorizons.messaging.packets.translators;

import nl.mthorizons.messaging.common.packets.PayloadPacket;
import org.geysermc.geyser.session.GeyserSession;
import org.geysermc.mcprotocollib.network.Session;

public interface PacketTranslator<T extends PayloadPacket> {

    Class<T> getType();

    void translate(GeyserSession session, T packet);
}

