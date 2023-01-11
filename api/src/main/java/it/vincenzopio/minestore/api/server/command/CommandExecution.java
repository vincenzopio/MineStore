package it.vincenzopio.minestore.api.server.command;

public class CommandExecution {

    public final String username;
    private final String command;

    public CommandExecution(String username, String command) {
        this.username = username;
        this.command = command;
    }

    public String getCommand() {
        return command;
    }

    public String getUsername() {
        return username;
    }
}
