package nl.mthorizons.messaging.common.packets;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EntityForceLinkPacket extends PayloadPacket {

    private Integer passenger;
    private Integer vehicle;

}
