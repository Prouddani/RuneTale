package me.prouddani.config;

import javax.annotation.Nullable;
import java.util.ArrayList;

public class JsonStructure {
    private boolean disableMobSpawning;
    private boolean removeMobs;
    public ArrayList<Spawner> spawners;

    public JsonStructure(boolean disableMobSpawning, boolean removeMobs, @Nullable ArrayList<Spawner> spawners) {
        this.disableMobSpawning = disableMobSpawning;
        this.removeMobs = removeMobs;

        this.spawners = spawners == null ? new ArrayList<Spawner>() : spawners;
    }

    public boolean isMobSpawningDisabled() {
        return disableMobSpawning;
    }
    public void setDisableMobSpawning(boolean disableMobSpawning) {
        this.disableMobSpawning = disableMobSpawning;
    }

    public boolean areMobsRemoved() {
        return removeMobs;
    }
    public void setRemoveMobs(boolean removeMobs) {
        this.removeMobs = removeMobs;
    }
}
