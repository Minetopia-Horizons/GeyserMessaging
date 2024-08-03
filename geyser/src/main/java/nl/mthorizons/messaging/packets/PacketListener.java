package nl.mthorizons.messaging.packets;

import lombok.Getter;
import nl.mthorizons.messaging.GeyserPlugin;
import nl.mthorizons.messaging.common.codecs.PacketCodec;
import nl.mthorizons.messaging.common.packets.PayloadPacket;
import org.geysermc.geyser.session.GeyserSession;
import org.geysermc.mcprotocollib.network.Session;
import org.geysermc.mcprotocollib.network.event.session.SessionAdapter;
import org.geysermc.mcprotocollib.network.packet.Packet;
import org.geysermc.mcprotocollib.protocol.packet.common.clientbound.ClientboundCustomPayloadPacket;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Getter
public class PacketListener extends SessionAdapter {

    private final List<GeyserSession> sessions = new ArrayList<>();
    private final PacketHandler handler = new PacketHandler();

    public void listen(GeyserSession session) {
        if (session.getDownstream() == null) {
            GeyserPlugin.getInstance().getScheduler().schedule(() -> {
                listen(session);
            }, 50, TimeUnit.MILLISECONDS);
            return;
        };

        session.getDownstream().getSession().addListener(this);
        sessions.add(session);
    }

    public void disconnect(GeyserSession session) {
        if (!sessions.contains(session)) return;
        session.getDownstream().getSession().removeListener(this);

        sessions.remove(session);
    }

    @Override
    public void packetReceived(Session session, Packet packet) {
        if (!(packet instanceof ClientboundCustomPayloadPacket clientPacket)) return;
        if (!clientPacket.getChannel().asString().equals("geysermessaging:main")) return;
        Optional<GeyserSession> optional = streamSession(session);
        if (optional.isEmpty()) return;

        PayloadPacket payloadPacket = PacketCodec.decodePacket(clientPacket.getData());
        handler.handle(optional.get(), payloadPacket);
    }

    public Optional<GeyserSession> streamSession(Session session) {
        return sessions.stream().filter(s -> s.getDownstream().getSession().equals(session)).findAny();
    }

}
