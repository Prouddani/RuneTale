package me.prouddani.custom.system;

import com.hypixel.hytale.math.vector.Vector3d;
import com.hypixel.hytale.math.vector.Vector3f;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.universe.Universe;
import me.prouddani.NoNaturalMobsPlugin;
import me.prouddani.components.NotNaturalTag;
import me.prouddani.config.Spawner;
import me.prouddani.events.SpawnNPCWithTag;
import me.prouddani.utils.Messaging;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ThreadLocalRandom;

class SpawnerInformation {
    private int num_spawned_entities = 0;
    Spawner spawner;
    boolean thread_running = false;

    public SpawnerInformation(Spawner spawner) {
        this.spawner = spawner;
    }

    public boolean addOneToNumSpawnedEntities() {
        if (num_spawned_entities + 1 > spawner.getMaxActiveEntity())
            return false;

        ++num_spawned_entities;
        return true;
    }
    public boolean removeOneToNumSpawnedEntities() {
        if (num_spawned_entities - 1 < 0)
            return false;

        --num_spawned_entities;
        return true;
    }
}

public class MobSpawnerSystem {
    private MobSpawnerSystem() {}

    public static void handleSpawner(Spawner spawner) {
        SpawnerInformation info = new SpawnerInformation(spawner);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Messaging.MessageAllPlayers(Message.raw("task ran!"));

                boolean shouldSpawn = info.addOneToNumSpawnedEntities();
                if (!shouldSpawn) // there are too many alive spawned entities
                    return;

                ThreadLocalRandom random = ThreadLocalRandom.current();
                Vector3d spawnerPos2 = spawner.getPosition().toVector3d();
                Vector3d targetPos = new Vector3d(random, 5);
                targetPos = spawnerPos2.add(
                        targetPos.getX(),
                        0,
                        targetPos.getZ()
                );

                SpawnNPCWithTag.dispatch(
                        Universe.get().getWorld(spawner.getWorldUUID()),
                        spawner.getEntityId(),
                        spawner.getPosition().toVector3d(),
                        new Vector3f(),
                        (npc, holder, store) -> {
                            holder.addComponent(NotNaturalTag.getComponentType(), new NotNaturalTag());
                        },
                        (npc, ref, store) -> {
                        }
                );
            }
        }, 0, (long) (spawner.getSpawnDeltaDelayInSeconds() * 1000));

    }
}
