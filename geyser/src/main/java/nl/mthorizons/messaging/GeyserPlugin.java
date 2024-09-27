package nl.mthorizons.messaging;

import lombok.Getter;
import lombok.SneakyThrows;
import nl.mthorizons.messaging.bedrock.translators.JavaAddEntityTranslator;
import nl.mthorizons.messaging.configs.MessagingConfig;
import nl.mthorizons.messaging.packets.PacketListener;
import nl.mthorizons.messaging.tasks.LinkTask;
import nl.mthorizons.messaging.utils.CommonUtils;
import org.geysermc.event.subscribe.Subscribe;
import org.geysermc.geyser.api.event.bedrock.SessionDisconnectEvent;
import org.geysermc.geyser.api.event.bedrock.SessionLoginEvent;
import org.geysermc.geyser.api.event.lifecycle.GeyserPostInitializeEvent;
import org.geysermc.geyser.api.event.lifecycle.GeyserPreReloadEvent;
import org.geysermc.geyser.api.extension.Extension;
import org.geysermc.geyser.registry.Registries;
import org.geysermc.geyser.session.GeyserSession;
import org.geysermc.mcprotocollib.protocol.packet.ingame.clientbound.entity.spawn.ClientboundAddEntityPacket;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.hocon.HoconConfigurationLoader;

import java.nio.file.Path;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Getter
public class GeyserPlugin implements Extension {

    @Getter
    private static GeyserPlugin instance;
    private final PacketListener listener = new PacketListener();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);

    private Path configPath;
    private MessagingConfig config;

    public GeyserPlugin() {
        instance = this;
    }

    @Subscribe
    public void postInit(GeyserPostInitializeEvent event) {
        this.logger().info("GeyserMessaging has loaded.");

        configPath = this.dataFolder().resolve("geysermessaging.conf");
        loadConfig();

        Registries.JAVA_PACKET_TRANSLATORS.register(ClientboundAddEntityPacket.class, new JavaAddEntityTranslator());
        //Registries.JAVA_PACKET_TRANSLATORS.register(ClientboundTeleportEntityPacket.class, new JavaTeleportEntityTranslator());
        CommonUtils.registerEntities();

        this.scheduler.scheduleAtFixedRate(new LinkTask(), 0, 50, TimeUnit.MILLISECONDS);
    }

    @Subscribe
    public void onReload(GeyserPreReloadEvent ignored) {
        logger().info("Reloading config!");
        loadConfig();
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

    @SneakyThrows
    private void loadConfig() {
        final HoconConfigurationLoader loader = HoconConfigurationLoader.builder()
                .path(configPath)
                .defaultOptions(configurationOptions -> configurationOptions.header("Geyser Messaging Configuration"))
                .prettyPrinting(true)
                .build();

        try {
            final CommentedConfigurationNode node = loader.load();
            config = node.get(MessagingConfig.class);
            loader.save(node);
        } catch (ConfigurateException e) {
            this.logger().error("Could not load config! " + e.getMessage());
            this.logger().error("Disabling Geyser Messaging...");
            e.printStackTrace();
            this.disable();
            return;
        }

        if (config == null) {
            this.logger().error("Could not load config! Disabling...");
            this.disable();
            return;
        }
    }

}