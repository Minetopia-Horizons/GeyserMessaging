package nl.mthorizons.messaging;

import lombok.Getter;
import nl.mthorizons.messaging.bedrock.translators.AddEntityTranslator;
import nl.mthorizons.messaging.bedrock.translators.SetPassengersTranslator;
import nl.mthorizons.messaging.packets.PacketListener;
import org.geysermc.event.subscribe.Subscribe;
import org.geysermc.geyser.api.event.bedrock.SessionDisconnectEvent;
import org.geysermc.geyser.api.event.bedrock.SessionLoginEvent;
import org.geysermc.geyser.api.event.lifecycle.GeyserPostInitializeEvent;
import org.geysermc.geyser.api.extension.Extension;
import org.geysermc.geyser.registry.Registries;
import org.geysermc.geyser.session.GeyserSession;
import org.geysermc.mcprotocollib.protocol.packet.ingame.clientbound.entity.ClientboundMoveEntityRotPacket;
import org.geysermc.mcprotocollib.protocol.packet.ingame.clientbound.entity.ClientboundSetPassengersPacket;
import org.geysermc.mcprotocollib.protocol.packet.ingame.clientbound.entity.spawn.ClientboundAddEntityPacket;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Getter
public class GeyserPlugin implements Extension {

    @Getter
    private static GeyserPlugin instance;
    private final PacketListener listener = new PacketListener();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);

    public GeyserPlugin() {
        instance = this;
    }

    @Subscribe
    public void postInit(GeyserPostInitializeEvent event) {
        this.logger().info("GeyserMessaging has loaded.");

        Registries.JAVA_PACKET_TRANSLATORS.register(ClientboundAddEntityPacket.class, new AddEntityTranslator());
        Registries.JAVA_PACKET_TRANSLATORS.register(ClientboundSetPassengersPacket.class, new SetPassengersTranslator());
    }

    @Subscribe
    public void sessionLogin(SessionLoginEvent event) {
        if (!(event.connection() instanceof GeyserSession session)) return;
        listener.listen(session);
    }

    @Subscribe
    public void sessionDisconnect(SessionDisconnectEvent event) {
        if (!(event.connection() instanceof GeyserSession session)) return;
        listener.disconnect(session);
    }

}