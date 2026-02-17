package me.prouddani;

import com.hypixel.hytale.logger.HytaleLogger;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import com.hypixel.hytale.server.core.universe.Universe;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.WorldConfig;
import me.prouddani.commands.GetSpawnerCommand;
import me.prouddani.commands.SpawningCommand;
import me.prouddani.components.NotNaturalTag;
import me.prouddani.config.JsonConfig;
import me.prouddani.events.SpawnNPCWithTag;
import me.prouddani.systems.CoordinateDebugPlaceSystem;
import me.prouddani.systems.MobSpawnerPlaceSystem;
import me.prouddani.systems.NoNaturalMobsSystem;
import me.prouddani.systems.NonNaturalMobSpawner;

import javax.annotation.Nonnull;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.logging.Level;

public class NoNaturalMobsPlugin extends JavaPlugin {
    public static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();

    public NoNaturalMobsPlugin(@Nonnull JavaPluginInit init) {
        super(init);
    }

    @Override
    public void setup()
    {
        // loads JsonConfig singleton
        try {
            JsonConfig.load();
        } catch (URISyntaxException e) {
            LOGGER.at(Level.WARNING).log("JsonConfig object was unable to load properly!");
            return;
        }

        var es_registry = getEntityStoreRegistry();
        var evn_registry = getEventRegistry();
        var cmd_registry = getCommandRegistry();

        var nntag = es_registry.registerComponent(
                NotNaturalTag.class,
                "Not natural tag",
                NotNaturalTag.CODEC
        );
        NotNaturalTag.setComponentType(nntag);
        
        cmd_registry.registerCommand(new SpawningCommand());
        cmd_registry.registerCommand(new GetSpawnerCommand());
        evn_registry.register(SpawnNPCWithTag.class, new NonNaturalMobSpawner());
        es_registry.registerSystem(new CoordinateDebugPlaceSystem());
        es_registry.registerSystem(new MobSpawnerPlaceSystem());

        // disables natural mob spawning
        Map<String, World> worlds = Universe.get().getWorlds();
        worlds.forEach((worldName, world) -> {
            WorldConfig config = world.getWorldConfig();
            config.setSpawningNPC(!JsonConfig.getSpawnerConfig().isMobSpawningDisabled());
            config.setIsSpawnMarkersEnabled(!JsonConfig.getSpawnerConfig().isMobSpawningDisabled());

            config.markChanged();
        });

        if (JsonConfig.getSpawnerConfig().areMobsRemoved()) {
            es_registry.registerSystem(new NoNaturalMobsSystem());
        }
    }

    @Override
    protected void shutdown() {
        JsonConfig.save();
    }
}
