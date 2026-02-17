package me.prouddani.config;

import com.hypixel.hytale.math.vector.Vector3i;

import java.util.UUID;

public class Spawner {
    private Vector3i position;
    private String entityId;

    private int maxActiveEntity;
    private float spawnDeltaDelaySeconds;

    private UUID worldUUID;

    public Spawner(Vector3i position, String entityId, int maxActiveEntity, float spawnDeltaDelaySeconds, UUID worldUUID) {
        this.position = position;
        this.entityId = entityId;

        this.maxActiveEntity = maxActiveEntity;
        this.spawnDeltaDelaySeconds = spawnDeltaDelaySeconds;
    }

    public Vector3i getPosition() {
        return position;
    }
    public void setPosition(Vector3i position) {
        this.position = position;
    }

    public String getEntityId() {
        return entityId;
    }
    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public int getMaxActiveEntity() {
        return maxActiveEntity;
    }
    public void setMaxActiveEntity(int maxActiveEntity) {
        this.maxActiveEntity = maxActiveEntity;
    }

    public float getSpawnDeltaDelayInSeconds() {
        return spawnDeltaDelaySeconds;
    }
    public void setSpawnDeltaDelayInSeconds(float seconds) {
        this.spawnDeltaDelaySeconds = seconds;
    }

    public UUID getWorldUUID() {
        return worldUUID;
    }
    public void setWorldUUID(UUID worldUUID) {
        this.worldUUID = worldUUID;
    }
}
