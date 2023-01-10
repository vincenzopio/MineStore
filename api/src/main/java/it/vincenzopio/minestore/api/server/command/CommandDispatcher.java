package it.vincenzopio.minestore.api.server.command;

public interface CommandDispatcher {

    boolean dispatchCommand(String command);

    void dispatchOnJoin(String username, String command);
}
