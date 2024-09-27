package nl.mthorizons.messaging.packets.translators;

import nl.mthorizons.messaging.common.packets.CustomEntityPacket;
import nl.mthorizons.messaging.common.packets.UpdatePropertyPacket;
import nl.mthorizons.messaging.property.PropertyRegistry;
import nl.mthorizons.messaging.utils.registry.CustomRegistries;
import org.geysermc.geyser.entity.type.Entity;
import org.geysermc.geyser.session.GeyserSession;

public class UpdatePropertyTranslator implements PacketTranslator<UpdatePropertyPacket> {
    @Override
    public Class<UpdatePropertyPacket> getType() {
        return UpdatePropertyPacket.class;
    }

    @Override
    public void translate(GeyserSession session, UpdatePropertyPacket packet) {
        Entity entity = session.getEntityCache().getEntityByJavaId(packet.getId());
        if (entity == null) return;

        if (packet.getValue() instanceof Boolean)
            PropertyRegistry.inst().updateBoolean(entity, packet.getName(), (Boolean) packet.getValue());

        else if (packet.getValue() instanceof Float)
            PropertyRegistry.inst().updateFloat(entity, packet.getName(), (Float) packet.getValue());

        else if (packet.getValue() instanceof Integer)
            PropertyRegistry.inst().updateInteger(entity, packet.getName(), (Integer) packet.getValue());

    }
}
