package nl.mthorizons.messaging.common.packets;

import lombok.*;
import nl.mthorizons.messaging.common.types.CommonEntityFlag;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EntityFlagPacket extends PayloadPacket {

    private int entityId;
    private CommonEntityFlag flag;

}
