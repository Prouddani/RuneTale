package me.prouddani.components;

import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.component.Component;
import com.hypixel.hytale.component.ComponentType;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

import javax.annotation.Nullable;

public class NotNaturalTag implements Component<EntityStore> {
    private static ComponentType<EntityStore, NotNaturalTag> TYPE;

    public static void setComponentType(ComponentType<EntityStore, NotNaturalTag> type) {
        TYPE = type;
    }

    public static ComponentType<EntityStore, NotNaturalTag> getComponentType() {
        return TYPE;
    }

    public static final BuilderCodec<NotNaturalTag> CODEC = BuilderCodec
            .builder(NotNaturalTag.class, NotNaturalTag::new)
                    .build();


    public NotNaturalTag() {
    }

    @Nullable
    @Override
    public Component<EntityStore> clone() {
        return new NotNaturalTag();
    }

    @Override
    public String toString() {
        return "NoNaturalTag:\n";
    }
}
