package me.prouddani;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.RemoveReason;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import com.hypixel.hytale.server.core.universe.Universe;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.WorldConfig;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.server.npc.entities.NPCEntity;
import com.hypixel.hytale.server.spawning.controllers.SpawnJobSystem;
import me.prouddani.components.NotNaturalTag;
import me.prouddani.events.SpawnNPCWithTag;
import me.prouddani.systems.NoNaturalMobsSystem;
import me.prouddani.systems.NonNaturalMobSpawner;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NoNaturalMobsPlugin extends JavaPlugin {
    public NoNaturalMobsPlugin(@Nonnull JavaPluginInit init) {
        super(init);
    }

    @Override
    public void setup()
    {
        var es_registry = getEntityStoreRegistry();
        var evn_registry = getEventRegistry();

        Map<String, World> worlds = Universe.get().getWorlds();
        for (var entries : worlds.entrySet()) {
            var worldName = entries.getKey();
            var world = entries.getValue();

            WorldConfig config = world.getWorldConfig();
            config.setSpawningNPC(false);
            config.setIsSpawnMarkersEnabled(false);

            config.markChanged();

            world.execute(() -> {
                Store<EntityStore> store = world.getEntityStore().getStore();
                List<Ref<EntityStore>> npcRefs = new ArrayList<>();

                store.forEachChunk(NPCEntity.getComponentType(), (archetypeChunk, commandBuffer) -> {
                    for (int i = 0; i < archetypeChunk.size(); ++i) {
                        npcRefs.add(archetypeChunk.getReferenceTo(i));
                    }
                });

                npcRefs.forEach((ref) -> {
                    store.removeEntity(ref, RemoveReason.REMOVE);
                });
            });
        }


//        var nntag = es_registry.registerComponent(
//                NotNaturalTag.class,
//                "Not natural tag",
//                NotNaturalTag.CODEC
//        );
//        NotNaturalTag.setComponentType(nntag);
//
//        es_registry.registerSystem(new NoNaturalMobsSystem());

        //evn_registry.register(SpawnNPCWithTag.class, new NonNaturalMobSpawner());
    }

    public void run() {

    }
}
