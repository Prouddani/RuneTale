package me.prouddani.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.hypixel.hytale.math.vector.Vector3i;
import com.hypixel.hytale.server.core.HytaleServer;
import com.hypixel.hytale.server.core.plugin.PluginManager;
import com.hypixel.hytale.server.core.universe.Universe;
import me.prouddani.NoNaturalMobsPlugin;
import me.prouddani.custom.system.MobSpawnerSystem;

import java.io.*;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class JsonConfig {
    private static final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    private static JsonStructure spawnerConfig;

    private JsonConfig() {

    }

    public static void load() throws URISyntaxException {
        File configJson = new File(PluginManager.MODS_PATH.resolve("mob_spawners_config.json").toUri());

        if (configJson.exists()) {
            // file exists
            try {
                FileReader reader = new FileReader(configJson);
                Type type = new TypeToken<JsonStructure>(){}.getType();

                spawnerConfig = gson.fromJson(reader, type);
                reader.close(); // close it

                for (Spawner spawner : spawnerConfig.spawners) {
                    MobSpawnerSystem.handleSpawner(spawner);
                }
            } catch (FileNotFoundException e) {
                NoNaturalMobsPlugin.LOGGER.atInfo().log("File was not read successfully.");
            } catch (IOException e) {
                NoNaturalMobsPlugin.LOGGER.atInfo().log("File got an error.");
            }
        }
        else {
            // file doesn't exist
            spawnerConfig = new JsonStructure(
                    false,
                    false
            );

            try {
                FileWriter writer = new FileWriter(configJson);
                gson.toJson(spawnerConfig, writer);

                writer.close();


            } catch (IOException e) {
                NoNaturalMobsPlugin.LOGGER.atInfo().log("Error in JsonConfig: could not write json successfully.");
            }
        }
    }

    public static void save() {
        File configJson = new File(PluginManager.MODS_PATH.resolve("mob_spawners_config.json").toUri());
        try {
            FileWriter writer = new FileWriter(configJson);
            gson.toJson(spawnerConfig, writer);

            writer.close();
        } catch (IOException e) {
            NoNaturalMobsPlugin.LOGGER.atInfo().log("Unable to write config file");
        }
    }

    public static JsonStructure getSpawnerConfig() {
        return spawnerConfig;
    }
}
