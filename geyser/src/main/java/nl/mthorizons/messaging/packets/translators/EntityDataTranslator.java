package nl.mthorizons.messaging.packets.translators;

import nl.mthorizons.messaging.common.packets.EntityDataPacket;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityDataTypes;
import org.geysermc.geyser.entity.type.Entity;
import org.geysermc.geyser.session.GeyserSession;

public class EntityDataTranslator implements PacketTranslator<EntityDataPacket> {
    @Override
    public Class<EntityDataPacket> getType() {
        return EntityDataPacket.class;
    }

    @Override
    public void translate(GeyserSession session, EntityDataPacket packet) {
        if (packet.getEntityId() == null) return;
        Entity entity = session.getEntityCache().getEntityByJavaId(packet.getEntityId());
        if (entity == null) return;

        if (packet.getScale() != null) entity.getDirtyMetadata().put(EntityDataTypes.SCALE, packet.getScale());
        if (packet.getHitboxWidth() != null) entity.setBoundingBoxWidth(packet.getHitboxWidth());
        if (packet.getHitboxHeight() != null) entity.setBoundingBoxHeight(packet.getHitboxHeight());

        entity.updateBedrockMetadata();
    }
}
