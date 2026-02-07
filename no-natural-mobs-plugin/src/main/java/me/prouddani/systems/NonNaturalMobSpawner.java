package me.prouddani.systems;

import com.hypixel.hytale.component.Ref;
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

        var store = event.world().getEntityStore().getStore();
        var pair = NPCPlugin.get().spawnNPC(store, event.npcId(), null, event.position(), event.rotation());
        if (pair == null) {
            NoNaturalMobsPlugin.LOGGER.atInfo().log("pair is null");
            return;
        }

        var ref = pair.key();
        if (!ref.isValid()) {
            NoNaturalMobsPlugin.LOGGER.atInfo().log("ref is invalid!");
            return;
        }

        NoNaturalMobsPlugin.LOGGER.atInfo().log("adding component");
        store.addComponent(ref, NotNaturalTag.getComponentType());
//        ModelAsset asset = ModelAsset.getAssetMap().getAsset(event.npcId());
//        Model model = Model.createScaledModel(asset, 1.0f);
//        Pair<Ref<EntityStore>, NPCEntity> pair = NPCPlugin.get().spawnEntity(
//                event.world().getEntityStore().getStore(),
//                roleIndex,
//                event.position(),
//                event.rotation(),
//                model,
//                (npc, holder, store) -> {
//                    // pre-add hook
//                    holder.addComponent(NotNaturalTag.getComponentType(), new NotNaturalTag());
//                    NoNaturalMobsPlugin.LOGGER.atInfo().log("added component");
//                },
//                (npc, ref, store) -> {
//                    // post-add hook if needed
//                }
//        );
    }
}
