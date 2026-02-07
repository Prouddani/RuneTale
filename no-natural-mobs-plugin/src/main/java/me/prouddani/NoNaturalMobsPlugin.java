package me.prouddani;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.RemoveReason;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.logger.HytaleLogger;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import com.hypixel.hytale.server.core.universe.Universe;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.WorldConfig;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.server.npc.NPCPlugin;
import com.hypixel.hytale.server.npc.entities.NPCEntity;
import com.hypixel.hytale.server.spawning.controllers.SpawnJobSystem;
import me.prouddani.commands.SpawningCommand;
import me.prouddani.components.NotNaturalTag;
import me.prouddani.events.SpawnNPCWithTag;
import me.prouddani.systems.NoNaturalMobsSystem;
import me.prouddani.systems.NonNaturalMobSpawner;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NoNaturalMobsPlugin extends JavaPlugin {
    public static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();

    public NoNaturalMobsPlugin(@Nonnull JavaPluginInit init) {
        super(init);
    }

    @Override
    public void setup()
    {
        var es_registry = getEntityStoreRegistry();
        var evn_registry = getEventRegistry();
        var cmd_registry = getCommandRegistry();

        cmd_registry.registerCommand(new SpawningCommand());

        Map<String, World> worlds = Universe.get().getWorlds();
        worlds.forEach((worldName, world) -> {
            WorldConfig config = world.getWorldConfig();
            config.setSpawningNPC(false);
            config.setIsSpawnMarkersEnabled(false);

            config.markChanged();
        });

        var nntag = es_registry.registerComponent(
                NotNaturalTag.class,
                "Not natural tag",
                NotNaturalTag.CODEC
        );
        NotNaturalTag.setComponentType(nntag);

        es_registry.registerSystem(new NoNaturalMobsSystem());
        evn_registry.register(SpawnNPCWithTag.class, new NonNaturalMobSpawner());
    }
}
