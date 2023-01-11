package it.vincenzopio.minestore.api.settings.connection.mode.socket;

public class SocketSettings {

    private int port = 33066;
    private String password = "socket_safe_password";

    public SocketSettings() {}


    public int getPort() {
        return port;
    }

    public String getPassword() {
        return password;
    }
}
