package nl.mthorizons.messaging.tasks;

import nl.mthorizons.messaging.GeyserPlugin;
import nl.mthorizons.messaging.utils.EntityUtils;
import nl.mthorizons.messaging.utils.registry.CustomRegistries;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityFlag;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityLinkData;
import org.cloudburstmc.protocol.bedrock.packet.SetEntityLinkPacket;
import org.geysermc.geyser.entity.type.Entity;

public class LinkTask implements Runnable {
    @Override
    public void run() {
        GeyserPlugin.getInstance().getListener().getSessions().forEach(session -> {
            CustomRegistries.LINKED_ENTITIES.get().forEach((passenger, vehicle) -> {
                Entity passengerEntity = session.getEntityCache().getEntityByJavaId(passenger);
                if (passengerEntity == null) return;

                EntityLinkData data = new EntityLinkData(passenger, vehicle, EntityLinkData.Type.PASSENGER, true, false);
                SetEntityLinkPacket packet = new SetEntityLinkPacket();
                packet.setEntityLink(data);
                session.sendUpstreamPacket(packet);

                passengerEntity.setFlag(EntityFlag.RIDING, true);
                passengerEntity.updateBedrockMetadata();

                System.out.println("Linked entity again.");
            });
        });
    }
}
