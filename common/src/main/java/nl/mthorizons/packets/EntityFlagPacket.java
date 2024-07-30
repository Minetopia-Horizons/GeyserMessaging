package nl.mthorizons.packets;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nl.mthorizons.types.CommonEntityFlag;

@Getter
@RequiredArgsConstructor
@Builder
public class EntityFlagPacket extends PayloadPacket {

    private int entityId;
    private CommonEntityFlag flag;

}
