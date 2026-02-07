package me.prouddani.commands;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.math.vector.Vector3f;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
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
                    holder.addComponent(NotNaturalTag.getComponentType(), new NotNaturalTag());
                },
                (npc, npc_ref, not_needed_arg) -> {

                }
        );
    }
}
