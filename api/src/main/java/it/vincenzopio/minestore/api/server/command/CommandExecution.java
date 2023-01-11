package it.vincenzopio.minestore.api.server.command;

public class CommandExecution {

    public final String username;
    private final String command;
    private final Runnable onExecute;

    public CommandExecution(String username, String command, Runnable onExecute) {
        this.username = username;
        this.command = command;
        this.onExecute = onExecute;
    }

    public String getCommand() {
        return command;
    }

    public String getUsername() {
        return username;
    }

    public Runnable onExecute() {
        return onExecute;
    }
}
