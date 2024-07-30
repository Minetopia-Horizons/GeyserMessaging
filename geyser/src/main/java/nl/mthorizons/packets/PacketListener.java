package nl.mthorizons.packets;

import nl.mthorizons.codecs.PacketCodec;
import org.geysermc.geyser.session.GeyserSession;
import org.geysermc.mcprotocollib.network.Session;
import org.geysermc.mcprotocollib.network.event.session.SessionAdapter;
import org.geysermc.mcprotocollib.network.packet.Packet;
import org.geysermc.mcprotocollib.protocol.packet.common.clientbound.ClientboundCustomPayloadPacket;

public class PacketListener extends SessionAdapter {

    private GeyserSession session;
    private final PacketHandler handler = new PacketHandler();

    public void listen(GeyserSession session) {
        this.session = session;

        if (session.getDownstream() == null) return;
        session.getDownstream().getSession().addListener(this);
    }

    public void disconnect(GeyserSession session) {
        if (this.session != session) return;
        session.getDownstream().getSession().removeListener(this);

        this.session = null;
    }

    @Override
    public void packetReceived(Session session, Packet packet) {
        if (!(packet instanceof ClientboundCustomPayloadPacket clientPacket)) return;

        PayloadPacket payloadPacket = PacketCodec.decodePacket(clientPacket.getData());
        handler.handle(payloadPacket);
    }

    public boolean isConnected() {
        return session != null;
    }
}
