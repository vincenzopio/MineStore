package it.vincenzopio.minestore.api.connection.handler.listener.message;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ListenerMessage {

    @JsonProperty("type")
    private String type;

    @JsonProperty("auth_id")
    private String authId;

    @JsonProperty("id")
    private int id;

    @JsonProperty("username")
    private String username;

    @JsonProperty("command")
    private String command;

    @JsonProperty("is_online_required")
    private boolean requireOnline;

    public String getCommand() {
        return command;
    }

    public String getType() {
        return type;
    }

    public String getAuthID() {
        return authId;
    }

    public String getUsername() {
        return username;
    }

    public int getId() {
        return id;
    }

    public boolean isRequiredOnline() {
        return requireOnline;
    }
}
