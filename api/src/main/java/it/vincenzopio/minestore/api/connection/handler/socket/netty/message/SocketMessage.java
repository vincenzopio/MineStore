package it.vincenzopio.minestore.api.connection.handler.socket.netty.message;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SocketMessage {

    @JsonProperty("username")
    private String username;

    @JsonProperty("password")
    private String password;

    @JsonProperty("command")
    private String command;

    @JsonProperty("is_online_required")
    private boolean requireOnline;


    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getCommand() {
        return command;
    }

    public boolean isRequiredOnline() {
        return requireOnline;
    }
}
