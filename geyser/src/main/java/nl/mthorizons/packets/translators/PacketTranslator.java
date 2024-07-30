package nl.mthorizons.packets.translators;

import nl.mthorizons.packets.PayloadPacket;

public interface PacketTranslator<T extends PayloadPacket> {

    Class<T> getType();

    void translate(T packet);
}

