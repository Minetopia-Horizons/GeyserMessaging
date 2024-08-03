package nl.mthorizons.messaging;

import lombok.Getter;
import nl.mthorizons.messaging.api.SpigotAPI;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class SpigotPlugin extends JavaPlugin {

    @Getter
    private static SpigotPlugin instance;

    private final SpigotAPI api = new SpigotAPI();

    @Override
    public void onEnable() {
        instance = this;
        getServer().getMessenger().registerOutgoingPluginChannel(this, "geysermessaging:main");
    }

}