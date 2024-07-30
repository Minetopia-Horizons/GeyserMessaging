package nl.mthorizons;

import lombok.Getter;
import nl.mthorizons.api.SpigotAPI;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class SpigotPlugin extends JavaPlugin {

    @Getter
    private static SpigotPlugin instance;

    private final SpigotAPI api = new SpigotAPI();

    @Override
    public void onEnable() {
        instance = this;
    }

}