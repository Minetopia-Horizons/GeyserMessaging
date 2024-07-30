package nl.mthorizons.packets;

import nl.mthorizons.packets.translators.EntityFlagTranslator;
import nl.mthorizons.packets.translators.PacketTranslator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("ALL")
public class PacketHandler {

    private final List<PacketTranslator> translators = new ArrayList<>();

    public PacketHandler() {
        register(new EntityFlagTranslator());
    }

    public void handle(PayloadPacket packet) {
        for (PacketTranslator translator : translators) {
            if (!translator.getType().isInstance(packet)) continue;
            translator.translate(packet);

            return;
        }
    }

    public void register(PacketTranslator... translators) {
        this.translators.addAll(Arrays.asList(translators));
    }

}
