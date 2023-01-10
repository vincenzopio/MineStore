package it.vincenzopio.minestore.api.server.user;

public interface PlayerResolver {

    boolean isOnline(String username);

    void sendChatMessage();
}
