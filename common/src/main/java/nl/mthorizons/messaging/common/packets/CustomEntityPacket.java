package nl.mthorizons.messaging.common.packets;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CustomEntityPacket extends PayloadPacket {

    private Integer id;
    private String name;

}
