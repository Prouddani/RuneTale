package me.prouddani.commands;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.command.commands.player.inventory.InventoryCommand;
import com.hypixel.hytale.server.core.command.commands.player.inventory.InventoryItemCommand;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.arguments.system.DefaultArg;
import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.inventory.Inventory;
import com.hypixel.hytale.server.core.inventory.ItemStack;
import com.hypixel.hytale.server.core.inventory.container.CombinedItemContainer;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import org.bson.*;

import javax.annotation.Nonnull;

public class GetSpawnerCommand extends AbstractPlayerCommand {
    private DefaultArg<String> npcIdArg;
    private DefaultArg<Integer> maxActiveArg;
    private DefaultArg<Double> spawnDeltaDelayArg;

    public GetSpawnerCommand() {
        super("spawner", "Get a mob spawner!");
        npcIdArg = withDefaultArg(
                "id",
                "The npc id (e.g. 'Skeleton') you want the spawner to spawn",
                ArgTypes.STRING,
                "Skeleton",
                "Defaults to skeleton."
        );
        maxActiveArg = withDefaultArg(
                "max-active",
                "The maximum quantity of active entities (npc). Useful at fixing entity cramping.",
                ArgTypes.INTEGER,
                5,
                "Defaults to 5 max active entities (npc)."
        );
        spawnDeltaDelayArg = withDefaultArg(
                "spawn-delay",
                "The time needed for another npc to spawn.",
                ArgTypes.DOUBLE,
                4.0,
                "Defaults to 4 seconds."
        );
    }

    @Override
    protected void execute(
            @Nonnull CommandContext context,
            @Nonnull Store<EntityStore> store,
            @Nonnull Ref<EntityStore> ref,
            @Nonnull PlayerRef playerRef,
            @Nonnull World world
    ) {
        String npcId = npcIdArg.get(context);
        int maxActive = maxActiveArg.get(context);
        double spawnDelay = spawnDeltaDelayArg.get(context);

        Player playerComponent = store.getComponent(ref, Player.getComponentType());
        assert playerComponent != null;

        BsonDocument metadata = new BsonDocument();
        metadata.put("npcId", new BsonString(npcId));
        metadata.put("maxActiveEntities", new BsonInt32(maxActive));
        metadata.put("spawnDelay", new BsonDouble(spawnDelay));

        ItemStack stack = new ItemStack("RuneTale_Mob_Spawner", 1, metadata);

        Inventory inventory = playerComponent.getInventory();
        CombinedItemContainer container = inventory.getCombinedHotbarFirst();
        container.addItemStack(stack);
    }
}
