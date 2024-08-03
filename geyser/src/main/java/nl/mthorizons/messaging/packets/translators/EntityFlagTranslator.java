package nl.mthorizons.messaging.packets.translators;

import nl.mthorizons.messaging.GeyserPlugin;
import nl.mthorizons.messaging.common.packets.EntityFlagPacket;
import nl.mthorizons.messaging.utils.CommonUtils;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityFlag;
import org.geysermc.geyser.api.entity.type.GeyserEntity;
import org.geysermc.geyser.session.GeyserSession;
import org.geysermc.mcprotocollib.network.Session;

public class EntityFlagTranslator implements PacketTranslator<EntityFlagPacket> {

    @Override
    public Class<EntityFlagPacket> getType() {
        return EntityFlagPacket.class;
    }

    @Override
    public void translate(GeyserSession session, EntityFlagPacket packet) {
        int id = packet.getEntityId();
        EntityFlag flag = CommonUtils.convertFlag(packet.getFlag());
        System.out.println("TESTTTT");

        // add value
        //session.getEntityCache().getEntityByJavaId(id).setFlag(flag, true);
        session.getPlayerEntity().getVehicle().setFlag(flag, true);
    }

}
