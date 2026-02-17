package me.prouddani.systems;

import com.hypixel.hytale.component.*;
import com.hypixel.hytale.component.query.Query;
import com.hypixel.hytale.component.system.EntityEventSystem;
import com.hypixel.hytale.math.vector.Vector3i;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
import com.hypixel.hytale.server.core.asset.type.item.config.Item;
import com.hypixel.hytale.server.core.event.events.ecs.PlaceBlockEvent;
import com.hypixel.hytale.server.core.inventory.ItemStack;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.Universe;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.UUID;

public class CoordinateDebugPlaceSystem extends EntityEventSystem<EntityStore, PlaceBlockEvent> {
    public CoordinateDebugPlaceSystem() {
        super(PlaceBlockEvent.class);
    }

    @Override
    public void handle(
            int i,
            @Nonnull ArchetypeChunk<EntityStore> archetypeChunk,
            @Nonnull Store<EntityStore> store,
            @Nonnull CommandBuffer<EntityStore> commandBuffer,
            @Nonnull PlaceBlockEvent placeBlockEvent
    ) {
        Ref<EntityStore> ref = archetypeChunk.getReferenceTo(i);
        PlayerRef playerRef = store.getComponent(ref, PlayerRef.getComponentType());

        Vector3i blockPos = placeBlockEvent.getTargetBlock();
        UUID uuid = playerRef.getWorldUuid();

        World world = Universe.get().getWorld(uuid);

        ItemStack stack = placeBlockEvent.getItemInHand();
        Item item = stack.getItem();

        if (item.getBlockId().equals("Spawner_Coordinate_Debug")) {
            // equals to the spawner coordinate debug block!
            playerRef.sendMessage(Message.raw(
                    "Placed block coordinates: {%d, %d, %d}".formatted(blockPos.getX(), blockPos.getY(), blockPos.getZ())
            ));
        }
    }

    @Nullable
    @Override
    public Query<EntityStore> getQuery() {
        return Archetype.of(PlayerRef.getComponentType());
    }
}
