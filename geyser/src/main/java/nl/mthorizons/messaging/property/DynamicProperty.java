package nl.mthorizons.messaging.property;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.geysermc.geyser.entity.type.Entity;

@Data
@AllArgsConstructor
public class DynamicProperty {

    private final String name;
    private final Integer propertyIndex;

    private boolean value;

}
