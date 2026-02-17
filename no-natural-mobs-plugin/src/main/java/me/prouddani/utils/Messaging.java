package me.prouddani.utils;

import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.Universe;

public class Messaging {
    private Messaging() {}

    public static void MessageAllPlayers(Message message) {
        MessagePlayersByQuery((playerRef) -> {
            return true;
        }, message);
    }

    public static void MessagePlayersByQuery(PlayerQuery query, Message message) {
        for (PlayerRef playerRef : Universe.get().getPlayers()) {
            boolean isQueried = query.run(playerRef);

            if (isQueried)
                playerRef.sendMessage(message);
        }
    }
}
