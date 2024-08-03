package nl.mthorizons.messaging.bedrock.translators;

import lombok.SneakyThrows;
import org.cloudburstmc.math.vector.Vector3f;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityDataType;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityDataTypes;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityFlag;
import org.geysermc.geyser.entity.EntityDefinition;
import org.geysermc.geyser.entity.type.Entity;
import org.geysermc.geyser.registry.Registries;
import org.geysermc.geyser.session.GeyserSession;
import org.geysermc.geyser.translator.protocol.PacketTranslator;
import org.geysermc.mcprotocollib.protocol.data.game.entity.metadata.MetadataType;
import org.geysermc.mcprotocollib.protocol.data.game.entity.type.EntityType;
import org.geysermc.mcprotocollib.protocol.packet.ingame.clientbound.entity.spawn.ClientboundAddEntityPacket;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class AddEntityTranslator extends PacketTranslator<ClientboundAddEntityPacket> {

    public static Map<Integer, EntityType> types = new HashMap<>();

    @SneakyThrows
    @Override
    public void translate(GeyserSession session, ClientboundAddEntityPacket packet) {
        types.put(packet.getEntityId(), packet.getType());

        EntityDefinition<?> definition = Registries.ENTITY_DEFINITIONS.get(packet.getType());
        if (definition == null) {
            session.getGeyser().getLogger().debug("Could not find entity definition with type " + packet.getType());
            return;
        }

        Vector3f pos = Vector3f.from(packet.getX(), packet.getY(), packet.getZ());
        Vector3f motion = Vector3f.from(packet.getMotionX(), packet.getMotionY(), packet.getMotionZ());

        Entity entity = definition.factory().create(
                session, packet.getEntityId(),
                session.getEntityCache().getNextEntityId().incrementAndGet(),
                packet.getUuid(), definition, pos, motion, packet.getYaw(), packet.getPitch(), packet.getHeadYaw());

        entity.setFlag(EntityFlag.WASD_CONTROLLED, true);
        entity.setFlag(EntityFlag.SADDLED, true);
        entity.setFlag(EntityFlag.INVISIBLE, false);
        entity.updateBedrockMetadata();

        System.out.println(packet.getEntityId() + " " + packet.getType() + " " + packet.getType() + " " + entity.getFlag(EntityFlag.WASD_CONTROLLED));

        session.getEntityCache().spawnEntity(entity);
    }

}
