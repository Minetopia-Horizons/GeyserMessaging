package nl.mthorizons.messaging.utils.registry;

import org.geysermc.geyser.entity.EntityDefinition;
import org.geysermc.geyser.registry.SimpleMappedRegistry;
import org.geysermc.geyser.registry.loader.RegistryLoaders;

import java.util.EnumMap;
import java.util.HashMap;

public class CustomRegistries {

    public static final SimpleMappedRegistry<String, EntityDefinition<?>> CUSTOM_ENTITY_DEFINITIONS;
    public static final SimpleMappedRegistry<Integer, String> CUSTOM_ENTITIES;
    public static final SimpleMappedRegistry<Integer, Integer> LINKED_ENTITIES; // key = passenger, value = vehicle

    static {
        CUSTOM_ENTITY_DEFINITIONS = SimpleMappedRegistry.create(RegistryLoaders.empty(HashMap::new));
        CUSTOM_ENTITIES = SimpleMappedRegistry.create(RegistryLoaders.empty(HashMap::new));
        LINKED_ENTITIES = SimpleMappedRegistry.create(RegistryLoaders.empty(HashMap::new));
    }


}
