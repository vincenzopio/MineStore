package it.vincenzopio.minestore.api.server.user;

import net.kyori.adventure.text.Component;

public interface PlayerResolver {
    boolean isOnline(String username);

    void sendChatMessage(String username, Component component);
}
