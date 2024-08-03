package nl.mthorizons.messaging.bedrock.translators;

import org.cloudburstmc.protocol.bedrock.data.entity.EntityFlag;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityLinkData;
import org.cloudburstmc.protocol.bedrock.packet.SetEntityLinkPacket;
import org.geysermc.geyser.entity.type.Entity;
import org.geysermc.geyser.session.GeyserSession;
import org.geysermc.geyser.translator.protocol.PacketTranslator;
import org.geysermc.geyser.translator.protocol.bedrock.entity.player.BedrockRiderJumpTranslator;
import org.geysermc.geyser.translator.protocol.java.entity.JavaSetEntityLinkTranslator;
import org.geysermc.geyser.translator.protocol.java.entity.JavaSetPassengersTranslator;
import org.geysermc.geyser.util.EntityUtils;
import org.geysermc.mcprotocollib.protocol.packet.ingame.clientbound.entity.ClientboundSetPassengersPacket;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SetPassengersTranslator extends PacketTranslator<ClientboundSetPassengersPacket> {
    @Override
    public void translate(GeyserSession session, ClientboundSetPassengersPacket packet) {
        System.out.println(AddEntityTranslator.types.get(packet.getEntityId()) + " " + Arrays.toString(packet.getPassengerIds()));

        Entity entity = session.getEntityCache().getEntityByJavaId(packet.getEntityId());
        if (entity == null) return;

        List<Entity> passengers = IntStream.of(packet.getPassengerIds())
                .mapToObj(session.getEntityCache()::getEntityByJavaId).filter(Objects::nonNull).toList();

        passengers.forEach(e -> {
            if (e == null) return;

            e.setVehicle(entity);
            e.setFlag(EntityFlag.RIDING, true);

            SetEntityLinkPacket linkPacket = new SetEntityLinkPacket();
            linkPacket.setEntityLink(new EntityLinkData(entity.getGeyserId(), e.getGeyserId(), EntityLinkData.Type.RIDER, true, true));
            session.sendUpstreamPacket(linkPacket);

            e.updateBedrockMetadata();
        });

        entity.setPassengers(passengers);
        entity.updateBedrockMetadata();
    }
}
