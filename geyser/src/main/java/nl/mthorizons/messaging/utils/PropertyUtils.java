package nl.mthorizons.messaging.utils;

import lombok.experimental.UtilityClass;
import org.geysermc.geyser.entity.properties.GeyserEntityProperties;
import org.geysermc.geyser.entity.properties.type.PropertyType;

@UtilityClass
public class PropertyUtils {

    public GeyserEntityProperties.Builder extract(GeyserEntityProperties properties) {
        GeyserEntityProperties.Builder builder = new GeyserEntityProperties.Builder();

        for (PropertyType p : properties.getProperties()) {
            String name = p.nbtMap().getString("name");
            int oldType = p.nbtMap().getInt("type");

            switch (oldType) {
                case 0:
                    builder.addInt(name);
                    break;
                case 1:
                    builder.addFloat(name);
                    break;
                case 2:
                    builder.addBoolean(name);
                    break;
            }
        }

        return builder;
    }

}
