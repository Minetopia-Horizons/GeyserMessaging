package nl.mthorizons.messaging.utils;

import lombok.experimental.UtilityClass;
import nl.mthorizons.messaging.common.types.CommonEntityFlag;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityFlag;

@UtilityClass
public class CommonUtils {
    public EntityFlag convertFlag(CommonEntityFlag flag) {
        return EntityFlag.valueOf(flag.name());
    }
}
