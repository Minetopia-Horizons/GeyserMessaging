package nl.mthorizons.utils;

import lombok.experimental.UtilityClass;
import nl.mthorizons.types.CommonEntityFlag;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityFlag;

@UtilityClass
public class CommonUtils {
    public EntityFlag convertFlag(CommonEntityFlag flag) {
        return EntityFlag.valueOf(flag.name());
    }
}
