package it.vincenzopio.minestore.api.settings.connection.mode.socket;

public class SocketSettings {

    private final int port;
    private final String password;

    public SocketSettings(int port, String password) {
        this.port = port;
        this.password = password;
    }

    public int getPort() {
        return port;
    }

    public String getPassword() {
        return password;
    }
}
