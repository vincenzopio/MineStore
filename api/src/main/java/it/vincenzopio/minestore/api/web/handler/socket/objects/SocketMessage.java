package it.vincenzopio.minestore.api.web.handler.socket.objects;

import com.google.gson.annotations.SerializedName;

public class SocketMessage {

    @SerializedName("username")
    private String username;

    @SerializedName("password")
    private String password;

    @SerializedName("command")
    private String command;

    @SerializedName("is_online_required")
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
