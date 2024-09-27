package nl.mthorizons.messaging.utils;

import lombok.experimental.UtilityClass;
import nl.mthorizons.messaging.utils.registry.CustomRegistries;
import org.cloudburstmc.nbt.NbtMap;
import org.cloudburstmc.nbt.NbtType;
import org.geysermc.geyser.entity.EntityDefinition;
import org.geysermc.geyser.entity.properties.GeyserEntityProperties;
import org.geysermc.geyser.registry.Registries;

import javax.swing.text.html.parser.Entity;
import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class EntityUtils {

    public void registerEntity(String id) {
        NbtMap registry = Registries.BEDROCK_ENTITY_IDENTIFIERS.get();
        List<NbtMap> idList = new ArrayList<>(registry.getList("idlist", NbtType.COMPOUND));
        idList.add(NbtMap.builder()
                .putString("id", id)
                .putString("bid", "")
                .putBoolean("hasspawnegg", false)
                .putInt("rid", idList.size() + 1)
                .putBoolean("summonable", false).build()
        );

        Registries.BEDROCK_ENTITY_IDENTIFIERS.set(NbtMap.builder()
                .putList("idlist", NbtType.COMPOUND, idList).build()
        );

        EntityDefinition def = EntityDefinition.builder(null)
                .height(0.1f).width(0.1f).identifier(id)
                .registeredProperties(new GeyserEntityProperties.Builder().build()).build();

        CustomRegistries.CUSTOM_ENTITY_DEFINITIONS.get().put(id, def);
    }

}
