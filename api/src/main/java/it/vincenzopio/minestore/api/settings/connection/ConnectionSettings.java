package it.vincenzopio.minestore.api.settings.connection;

import it.vincenzopio.minestore.api.settings.connection.mode.ConnectionMode;
import it.vincenzopio.minestore.api.settings.connection.mode.listener.ListenerSettings;
import it.vincenzopio.minestore.api.settings.connection.mode.socket.SocketSettings;

public class ConnectionSettings {

    private final ConnectionMode connectionMode;
    private final SocketSettings socketSettings;
    private final ListenerSettings listenerSettings;

    public ConnectionSettings(ConnectionMode connectionMode, SocketSettings socketSettings, ListenerSettings listenerSettings) {
        this.connectionMode = connectionMode;
        this.socketSettings = socketSettings;
        this.listenerSettings = listenerSettings;
    }

    public ConnectionMode getConnectionMode() {
        return connectionMode;
    }

    public SocketSettings getSocketConfig() {
        return socketSettings;
    }

    public ListenerSettings getListenerConfig() {
        return listenerSettings;
    }


}
