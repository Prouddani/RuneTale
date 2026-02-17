package me.prouddani.systems;

import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.ExtraInfo;
import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.codec.schema.SchemaContext;
import com.hypixel.hytale.codec.schema.config.Schema;
import com.hypixel.hytale.component.*;
import com.hypixel.hytale.component.query.Query;
import com.hypixel.hytale.component.system.EntityEventSystem;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.event.events.ecs.PlaceBlockEvent;
import com.hypixel.hytale.server.core.inventory.ItemStack;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import me.prouddani.config.JsonConfig;
import me.prouddani.config.Spawner;
import me.prouddani.custom.system.MobSpawnerSystem;
import me.prouddani.events.SpawnNPCWithTag;
import org.bson.BsonDocument;
import org.bson.BsonValue;
import org.bson.types.Code;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.Supplier;

public class MobSpawnerPlaceSystem extends EntityEventSystem<EntityStore, PlaceBlockEvent> {
    public MobSpawnerPlaceSystem() {
        super(PlaceBlockEvent.class);
    }

    @Override
    public void handle(
            int i,
            @Nonnull ArchetypeChunk<EntityStore> archetypeChunk,
            @Nonnull Store<EntityStore> store,
            @Nonnull CommandBuffer<EntityStore> commandBuffer,
            @Nonnull PlaceBlockEvent event
    ) {
        World world = store.getExternalData().getWorld();

        Ref<EntityStore> ref = archetypeChunk.getReferenceTo(i);
        PlayerRef playerRef = store.getComponent(ref, PlayerRef.getComponentType());

        ItemStack held = event.getItemInHand();
        assert held != null;

        if (!held.getItemId().equals("RuneTale_Mob_Spawner"))
            return;

        String npcId = held.getFromMetadataOrNull("npcId", Codec.STRING);
        Integer maxActive = held.getFromMetadataOrNull("maxActiveEntities", Codec.INTEGER);
        Double spawnDelay = held.getFromMetadataOrNull("spawnDelay", Codec.DOUBLE);

        if ((npcId == null || maxActive == null || spawnDelay == null)) {
            if (playerRef != null && playerRef.isValid())
                playerRef.sendMessage(Message.raw(
                        "Unsuccessful metadata reading:\nNpcId: %s\nMaxActiveEntities: %d\nSpawnDelay: %f".formatted(
                                npcId, maxActive, spawnDelay
                        )
                ));

            return;
        }

        if (playerRef != null && playerRef.isValid())
            playerRef.sendMessage(Message.raw(
                    "Successfully read metadata:\nNpcId: %s\nMaxActiveEntities: %d\nSpawnDelay: %f".formatted(
                            npcId, maxActive, spawnDelay
                    )
            ));

        Spawner spawner = new Spawner(
                event.getTargetBlock(),
                npcId,
                maxActive,
                spawnDelay.floatValue(),
                world.getWorldConfig().getUuid()
        );
        JsonConfig.getSpawnerConfig().spawners.add(spawner);
        MobSpawnerSystem.handleSpawner(spawner);
    }

    @Nullable
    @Override
    public Query<EntityStore> getQuery() {
        return Archetype.of(PlayerRef.getComponentType());
    }
}
