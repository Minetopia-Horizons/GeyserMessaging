package nl.mthorizons.codecs;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.experimental.UtilityClass;
import nl.mthorizons.packets.PayloadPacket;

import java.io.IOException;

@UtilityClass
public class PacketCodec {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.JAVA_LANG_OBJECT, JsonTypeInfo.As.PROPERTY);
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
    }


    public byte[] encodePacket(PayloadPacket packet) {

        try {
            return objectMapper.writeValueAsBytes(packet);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public PayloadPacket decodePacket(byte[] bytes) {
        try {
            return objectMapper.readValue(bytes, PayloadPacket.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
