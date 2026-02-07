package me.prouddani.systems;

import com.hypixel.hytale.component.*;
import com.hypixel.hytale.component.query.Query;
import com.hypixel.hytale.component.system.RefSystem;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import me.prouddani.components.NotNaturalTag;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class NoNaturalMobsSystem extends RefSystem<EntityStore> {
    @Override
    public void onEntityAdded(
            @Nonnull Ref<EntityStore> ref,
            @Nonnull AddReason addReason,
            @Nonnull Store<EntityStore> store,
            @Nonnull CommandBuffer<EntityStore> commandBuffer
    ) {
        if (!ref.isValid())
            return;

        commandBuffer.removeEntity(ref, RemoveReason.REMOVE);
    }

    @Override
    public void onEntityRemove(
            @Nonnull Ref<EntityStore> ref,
            @Nonnull RemoveReason removeReason,
            @Nonnull Store<EntityStore> store,
            @Nonnull CommandBuffer<EntityStore> commandBuffer
    ) {

    }

    @Nullable
    @Override
    public Query<EntityStore> getQuery() {
        return Query.and(
                Query.not(Archetype.of(PlayerRef.getComponentType())),
                Query.not(Archetype.of(NotNaturalTag.getComponentType()))
        );
    }
}
