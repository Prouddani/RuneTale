package me.prouddani.events;

import com.hypixel.hytale.component.Holder;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.event.IEvent;
import com.hypixel.hytale.event.IEventDispatcher;
import com.hypixel.hytale.function.consumer.TriConsumer;
import com.hypixel.hytale.math.vector.Vector3d;
import com.hypixel.hytale.math.vector.Vector3f;
import com.hypixel.hytale.server.core.HytaleServer;
import com.hypixel.hytale.server.core.asset.type.model.config.Model;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.server.npc.entities.NPCEntity;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public record SpawnNPCWithTag(
        World world,
        String npcId,
        @Nonnull Vector3d position,
        @Nonnull Vector3f rotation,
        @Nullable TriConsumer<NPCEntity, Holder<EntityStore>, Store<EntityStore>> preAddToWorld,
        @Nullable TriConsumer<NPCEntity, Ref<EntityStore>, Store<EntityStore>> postSpawn
) implements IEvent<Void> {
    private static void dispatch(
            World world,
            String npcId,
            @Nonnull Vector3d position,
            @Nonnull Vector3f rotation,
            @Nullable TriConsumer<NPCEntity, Holder<EntityStore>, Store<EntityStore>> preAddToWorld,
            @Nullable TriConsumer<NPCEntity, Ref<EntityStore>, Store<EntityStore>> postSpawn
    ) {
        IEventDispatcher<SpawnNPCWithTag, SpawnNPCWithTag> dispatcher =
                HytaleServer.get().getEventBus().dispatchFor(SpawnNPCWithTag.class);

        if (dispatcher.hasListener()) {
            dispatcher.dispatch(new SpawnNPCWithTag(world, npcId, position, rotation, preAddToWorld, postSpawn));
        }
    }
}
