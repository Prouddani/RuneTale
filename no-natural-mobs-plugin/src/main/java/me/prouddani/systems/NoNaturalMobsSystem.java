package me.prouddani.systems;

import com.hypixel.hytale.component.*;
import com.hypixel.hytale.component.query.Query;
import com.hypixel.hytale.component.system.RefSystem;
import com.hypixel.hytale.component.system.tick.EntityTickingSystem;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import me.prouddani.components.NotNaturalTag;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class NoNaturalMobsSystem extends EntityTickingSystem<EntityStore> {
    @Override
    public void tick(
            float dt,
            int i,
            @Nonnull ArchetypeChunk<EntityStore> archetypeChunk,
            @Nonnull Store<EntityStore> store,
            @Nonnull CommandBuffer<EntityStore> commandBuffer
    ) {
        Ref<EntityStore> ref = archetypeChunk.getReferenceTo(i);

        if (ref.isValid())
            commandBuffer.tryRemoveEntity(ref, RemoveReason.REMOVE);
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
