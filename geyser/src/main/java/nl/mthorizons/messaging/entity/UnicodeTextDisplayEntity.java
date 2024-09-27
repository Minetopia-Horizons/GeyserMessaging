package nl.mthorizons.messaging.entity;

import net.kyori.adventure.text.Component;
import nl.mthorizons.messaging.GeyserPlugin;
import org.cloudburstmc.math.vector.Vector3f;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityDataTypes;
import org.geysermc.geyser.entity.EntityDefinition;
import org.geysermc.geyser.entity.type.TextDisplayEntity;
import org.geysermc.geyser.session.GeyserSession;
import org.geysermc.geyser.translator.text.MessageTranslator;
import org.geysermc.mcprotocollib.protocol.data.game.entity.metadata.EntityMetadata;

import java.util.UUID;
import java.util.stream.Collectors;

public class UnicodeTextDisplayEntity extends TextDisplayEntity {
    public UnicodeTextDisplayEntity(GeyserSession session, int entityId, long geyserId, UUID uuid, EntityDefinition<?> definition, Vector3f position, Vector3f motion, float yaw, float pitch, float headYaw) {
        super(session, entityId, geyserId, uuid, definition, position, motion, yaw, pitch, headYaw);
    }

    @Override
    public void setText(EntityMetadata<Component, ?> entityMetadata) {
        String text = MessageTranslator.convertMessage(entityMetadata.getValue());
        this.dirtyMetadata.put(EntityDataTypes.NAME, removeUnicode(text));
    }

    private String removeUnicode(String text) {
        String pattern = String.join("", GeyserPlugin.getInstance().getConfig().getBlockedCharacters());
        return text.replaceAll("[" + pattern + "]", "");
    }
}
