package nl.mthorizons.packets.translators;

import nl.mthorizons.GeyserPlugin;
import nl.mthorizons.packets.EntityFlagPacket;

public class EntityFlagTranslator implements PacketTranslator<EntityFlagPacket> {

    @Override
    public Class<EntityFlagPacket> getType() {
        return EntityFlagPacket.class;
    }

    @Override
    public void translate(EntityFlagPacket packet) {
        GeyserPlugin.getInstance().logger().info("Translating fr fr?");
    }

}
