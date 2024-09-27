package nl.mthorizons.messaging.packets.translators;

import nl.mthorizons.messaging.common.packets.CustomEntityPacket;
import nl.mthorizons.messaging.common.packets.EntityForceLinkPacket;
import nl.mthorizons.messaging.utils.registry.CustomRegistries;
import org.geysermc.geyser.session.GeyserSession;

public class EntityForceLinkTranslator implements PacketTranslator<EntityForceLinkPacket> {
    @Override
    public Class<EntityForceLinkPacket> getType() {
        return EntityForceLinkPacket.class;
    }

    @Override
    public void translate(GeyserSession session, EntityForceLinkPacket packet) {
        CustomRegistries.LINKED_ENTITIES.get().put(packet.getPassenger(), packet.getVehicle());
    }
}
