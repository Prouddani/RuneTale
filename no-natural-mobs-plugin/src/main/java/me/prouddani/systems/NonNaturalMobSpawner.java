package me.prouddani.systems;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.event.IEventDispatcher;
import com.hypixel.hytale.server.core.HytaleServer;
import com.hypixel.hytale.server.core.asset.type.model.config.Model;
import com.hypixel.hytale.server.core.asset.type.model.config.ModelAsset;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.server.npc.NPCPlugin;
import com.hypixel.hytale.server.npc.entities.NPCEntity;
import it.unimi.dsi.fastutil.Pair;
import me.prouddani.NoNaturalMobsPlugin;
import me.prouddani.components.NotNaturalTag;
import me.prouddani.events.SpawnNPCWithTag;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public class NonNaturalMobSpawner implements Consumer<SpawnNPCWithTag> {
    @Override
    public void accept(SpawnNPCWithTag event) {
        var roleIndex = NPCPlugin.get().getIndex(event.npcId());
        if (roleIndex < 0) {
            NoNaturalMobsPlugin.LOGGER.atInfo().log("npc doesn't exist!");
            return;
        }

        NoNaturalMobsPlugin.LOGGER.atInfo().log("sir yes sir!");

        ModelAsset asset = ModelAsset.getAssetMap().getAsset(event.npcId());
        if (asset == null) {
            NoNaturalMobsPlugin.LOGGER.atInfo().log("model asset is invalid!");
            return;
        }

        Model model = Model.createScaledModel(asset, 1.0f);

        var store = event.world().getEntityStore().getStore();
        var pair = NPCPlugin.get().spawnEntity(
                store, roleIndex, event.position(), event.rotation(), model,
                (npc, holder, store2) -> {
                    holder.addComponent(NotNaturalTag.getComponentType(), new NotNaturalTag());

                    if (event.preAddToWorld() != null)
                        event.preAddToWorld().accept(npc, holder, store2);
                },
                (npc, ref, store2) -> {
                    if (event.postSpawn() != null)
                        event.postSpawn().accept(npc, ref, store2);
                }
        );
    }
}
