package me.prouddani.systems;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.server.core.asset.type.model.config.Model;
import com.hypixel.hytale.server.core.asset.type.model.config.ModelAsset;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.server.npc.NPCPlugin;
import com.hypixel.hytale.server.npc.entities.NPCEntity;
import it.unimi.dsi.fastutil.Pair;
import me.prouddani.components.NotNaturalTag;
import me.prouddani.events.SpawnNPCWithTag;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public class NonNaturalMobSpawner implements Consumer<SpawnNPCWithTag> {
    @Override
    public void accept(SpawnNPCWithTag event) {
        var roleIndex = NPCPlugin.get().getIndex(event.npcId());
        if (roleIndex < 0) {
            return;
        }

        ModelAsset asset = ModelAsset.getAssetMap().getAsset(event.npcId());
        Model model = Model.createScaledModel(asset, 1.0f);
        Pair<Ref<EntityStore>, NPCEntity> pair = NPCPlugin.get().spawnEntity(
                event.world().getEntityStore().getStore(),
                roleIndex,
                event.position(),
                event.rotation(),
                model,
                (npc, holder, store) -> {
                    // pre-add hook
                    holder.addComponent(NotNaturalTag.getComponentType(), new NotNaturalTag());
                },
                (npc, ref, store) -> {
                    // post-add hook if needed
                }
        );
    }
}
