package me.prouddani.commands;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.RemoveReason;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.math.vector.Vector3f;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
import com.hypixel.hytale.server.core.modules.entitystats.EntityStatMap;
import com.hypixel.hytale.server.core.modules.entitystats.EntityStatValue;
import com.hypixel.hytale.server.core.modules.entitystats.asset.DefaultEntityStatTypes;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import me.prouddani.NoNaturalMobsPlugin;
import me.prouddani.components.NotNaturalTag;
import me.prouddani.events.SpawnNPCWithTag;

import javax.annotation.Nonnull;

public class SpawningCommand extends AbstractPlayerCommand {
    public SpawningCommand() {
        super("summon", "Summons 1 skeleton with the tag");
    }

    @Override
    protected void execute(
            @Nonnull CommandContext context,
            @Nonnull Store<EntityStore> store,
            @Nonnull Ref<EntityStore> ref,
            @Nonnull PlayerRef playerRef,
            @Nonnull World world
    ) {
        TransformComponent transform = store.getComponent(ref, TransformComponent.getComponentType());
        if (transform == null)
            return;

        SpawnNPCWithTag.dispatch(
                world, "Skeleton", transform.getPosition(), new Vector3f(),
                (npc, holder, not_needed_arg) -> {
    
                },
                (npc, npc_ref, not_needed_arg) -> {
                    NoNaturalMobsPlugin.LOGGER.atInfo().log("worked!");
//                    EntityStatMap map = store.getComponent(npc_ref, EntityStatMap.getComponentType());
//                    if (map == null)
//                        return;
//
//                    map.setStatValue(DefaultEntityStatTypes.getHealth(), 10);
                }
        );
    }
}
