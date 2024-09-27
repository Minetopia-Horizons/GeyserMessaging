package nl.mthorizons.messaging.utils;

import lombok.experimental.UtilityClass;
import nl.mthorizons.messaging.GeyserPlugin;
import nl.mthorizons.messaging.entity.UnicodeTextDisplayEntity;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityFlag;
import org.geysermc.geyser.entity.EntityDefinition;
import org.geysermc.geyser.entity.type.DisplayBaseEntity;
import org.geysermc.geyser.entity.type.Entity;
import org.geysermc.geyser.registry.Registries;
import org.geysermc.mcprotocollib.protocol.data.game.entity.metadata.MetadataType;
import org.geysermc.mcprotocollib.protocol.data.game.entity.type.EntityType;

@UtilityClass
public class CommonUtils {

    public void registerEntities() {
        EntityDefinition<Entity> entityBase = EntityDefinition.builder(Entity::new)
                .addTranslator(MetadataType.BYTE, Entity::setFlags)
                .addTranslator(MetadataType.INT, Entity::setAir)
                .addTranslator(MetadataType.OPTIONAL_CHAT, Entity::setDisplayName)
                .addTranslator(MetadataType.BOOLEAN, Entity::setDisplayNameVisible)
                .addTranslator(MetadataType.BOOLEAN, Entity::setSilent)
                .addTranslator(MetadataType.BOOLEAN, Entity::setGravity)
                .addTranslator(MetadataType.POSE, (entity, entityMetadata) -> { entity.setPose(entityMetadata.getValue()); })
                .addTranslator(MetadataType.INT, Entity::setFreezing).build();;

        EntityDefinition<DisplayBaseEntity> livingEntityBase = EntityDefinition.inherited(DisplayBaseEntity::new, entityBase)
                .addTranslator(null).addTranslator(null)
                .addTranslator(null).addTranslator(MetadataType.VECTOR3, DisplayBaseEntity::setTranslation)
                .addTranslator(null).addTranslator(null).addTranslator(null)
                .addTranslator(null).addTranslator(null)
                .addTranslator(null).addTranslator(null)
                .addTranslator(null).addTranslator(null)
                .addTranslator(null).addTranslator(null).build();

        EntityDefinition<UnicodeTextDisplayEntity> textDisplay = EntityDefinition.inherited(UnicodeTextDisplayEntity::new, livingEntityBase)
                .type(EntityType.TEXT_DISPLAY).identifier("minecraft:armor_stand")
                .offset(-0.5F).addTranslator(MetadataType.CHAT, UnicodeTextDisplayEntity::setText)
                .addTranslator(null).addTranslator(null)
                .addTranslator(null).addTranslator(null).build();

        var map = Registries.ENTITY_DEFINITIONS.get();
        map.replace(EntityType.TEXT_DISPLAY, textDisplay);
        Registries.ENTITY_DEFINITIONS.set(map);

        GeyserPlugin.getInstance().logger().info("Registered text display definition.");
    }

}
