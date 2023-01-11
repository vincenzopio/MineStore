package it.vincenzopio.minestore.api.server.command;

public class CommandExecution {

    public String username;
    private String command;

    public CommandExecution() {
    }

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
