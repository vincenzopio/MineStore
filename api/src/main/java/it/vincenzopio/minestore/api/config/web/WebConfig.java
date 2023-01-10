package it.vincenzopio.minestore.api.config.web;

import it.vincenzopio.minestore.api.config.web.socket.SocketConfig;

public class WebConfig {

    private final ConnectionMode connectionMode;
    private final SocketConfig socketConfig;

    public WebConfig(ConnectionMode connectionMode, SocketConfig socketConfig) {
        this.connectionMode = connectionMode;
        this.socketConfig = socketConfig;
    }

    public ConnectionMode getConnectionMode() {
        return connectionMode;
    }

    public SocketConfig getSocketConfig() {
        return socketConfig;
    }


}
