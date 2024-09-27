package nl.mthorizons.messaging.packets.translators;

import nl.mthorizons.messaging.common.packets.CustomEntityPacket;
import nl.mthorizons.messaging.utils.registry.CustomRegistries;
import org.geysermc.geyser.session.GeyserSession;

public class CustomEntityTranslator implements PacketTranslator<CustomEntityPacket> {
    @Override
    public Class<CustomEntityPacket> getType() {
        return CustomEntityPacket.class;
    }

    @Override
    public void translate(GeyserSession session, CustomEntityPacket packet) {
        CustomRegistries.CUSTOM_ENTITIES.get().put(packet.getId(), packet.getName());
    }
}
