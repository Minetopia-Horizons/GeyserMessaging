package nl.mthorizons.messaging.packets;

import nl.mthorizons.messaging.common.packets.PayloadPacket;
import nl.mthorizons.messaging.packets.translators.EntityFlagTranslator;
import nl.mthorizons.messaging.packets.translators.PacketTranslator;
import org.geysermc.geyser.session.GeyserSession;
import org.geysermc.mcprotocollib.network.Session;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("ALL")
public class PacketHandler {

    private final List<PacketTranslator> translators = new ArrayList<>();

    public PacketHandler() {
        register(new EntityFlagTranslator());
    }

    public void handle(GeyserSession session, PayloadPacket packet) {
        for (PacketTranslator translator : translators) {
            if (!translator.getType().isInstance(packet)) continue;
            translator.translate(session, packet);

            return;
        }
    }

    public void register(PacketTranslator... translators) {
        this.translators.addAll(Arrays.asList(translators));
    }

}
