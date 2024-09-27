package nl.mthorizons.messaging.property;

import nl.mthorizons.messaging.GeyserPlugin;
import nl.mthorizons.messaging.utils.PropertyUtils;
import nl.mthorizons.messaging.utils.registry.CustomRegistries;
import org.geysermc.api.Geyser;
import org.geysermc.geyser.entity.EntityDefinition;
import org.geysermc.geyser.entity.properties.GeyserEntityProperties;
import org.geysermc.geyser.entity.type.Entity;
import org.geysermc.geyser.registry.Registries;
import org.geysermc.geyser.session.GeyserSession;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PropertyRegistry {

    private static final PropertyRegistry inst = new PropertyRegistry();

    private final List<String> dynamicEntities = new ArrayList<>();

    public void updateInteger(Entity entity, String identifier, Integer value) {
        if (entity.getPropertyManager() == null) return;

        entity.getPropertyManager().add(identifier, value);
        entity.updateBedrockEntityProperties();
    }

    public void updateFloat(Entity entity, String identifier, Float value) {
        if (entity.getPropertyManager() == null) return;

        entity.getPropertyManager().add(identifier, value);
        entity.updateBedrockEntityProperties();
    }

    public void updateBoolean(Entity entity, String identifier, Boolean value) {
        if (entity.getPropertyManager() == null) return;

        entity.getPropertyManager().add(identifier, value);
        entity.updateBedrockEntityProperties();
    }

    public void register(String entityId, String identifier, Class<?> type) {
        EntityDefinition<?> definition = CustomRegistries.CUSTOM_ENTITY_DEFINITIONS.get(entityId);
        if (definition == null) return;

        GeyserEntityProperties.Builder builder = PropertyUtils.extract(definition.registeredProperties());
        if (type == Integer.class) builder.addInt(identifier);
        else if (type == Float.class) builder.addFloat(identifier);
        else if (type == Boolean.class) builder.addBoolean(identifier);

        GeyserEntityProperties properties = builder.build();
        CustomRegistries.CUSTOM_ENTITY_DEFINITIONS.get().replace(entityId, new EntityDefinition(
                definition.factory(), definition.entityType(), definition.identifier(),
                definition.width(), definition.height(), definition.offset(), properties, definition.translators()));

        Registries.BEDROCK_ENTITY_PROPERTIES.get().removeIf(n -> n.getString("type").equals(entityId));
        Registries.BEDROCK_ENTITY_PROPERTIES.get().add(properties.toNbtMap(entityId));
    }

    public static PropertyRegistry inst() {
        return inst;
    }

}
