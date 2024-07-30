package nl.mthorizons;

import lombok.Getter;
import nl.mthorizons.packets.PacketListener;
import org.geysermc.event.subscribe.Subscribe;
import org.geysermc.geyser.api.event.bedrock.SessionDisconnectEvent;
import org.geysermc.geyser.api.event.bedrock.SessionJoinEvent;
import org.geysermc.geyser.api.event.bedrock.SessionLoginEvent;
import org.geysermc.geyser.api.event.lifecycle.GeyserPostInitializeEvent;
import org.geysermc.geyser.api.extension.Extension;
import org.geysermc.geyser.session.GeyserSession;

@Getter
public class GeyserPlugin implements Extension {

    @Getter
    private static GeyserPlugin instance;
    private final PacketListener listener = new PacketListener();

    public GeyserPlugin() {
        instance = this;
    }

    @Subscribe
    public void postInit(GeyserPostInitializeEvent event) {
        this.logger().info("Loading geyser messaging...");
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