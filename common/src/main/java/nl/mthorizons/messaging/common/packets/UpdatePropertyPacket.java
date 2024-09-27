package nl.mthorizons.messaging.common.packets;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePropertyPacket extends PayloadPacket {

    private Integer id;

    private String name;
    private Object value;

}
