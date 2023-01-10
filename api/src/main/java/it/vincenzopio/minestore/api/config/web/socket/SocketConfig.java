package it.vincenzopio.minestore.api.config.web.socket;

public class SocketConfig {

    private final int port;
    private final String password;

    public SocketConfig(int port, String password) {
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
