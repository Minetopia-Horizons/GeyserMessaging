package nl.mthorizons.messaging.common.packets;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EntityDataPacket extends PayloadPacket {

    private Integer entityId;

    private Float scale;
    private Float hitboxWidth;
    private Float hitboxHeight;

}
