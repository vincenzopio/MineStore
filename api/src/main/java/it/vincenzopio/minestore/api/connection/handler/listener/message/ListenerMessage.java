package it.vincenzopio.minestore.api.connection.handler.listener.message;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListenerMessage {

    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("auth_id")
    private String authId;

    @SerializedName("id")
    private int id;

    @SerializedName("username")
    private String username;

    @SerializedName("command")
    private String command;

    @SerializedName("is_online_required")
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
