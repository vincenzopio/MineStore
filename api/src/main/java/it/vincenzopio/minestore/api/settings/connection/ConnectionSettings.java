package it.vincenzopio.minestore.api.settings.connection;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.vincenzopio.minestore.api.settings.connection.mode.ConnectionMode;
import it.vincenzopio.minestore.api.settings.connection.mode.listener.ListenerSettings;
import it.vincenzopio.minestore.api.settings.connection.mode.socket.SocketSettings;

public class ConnectionSettings {

    @JsonProperty("mode")
    private ConnectionMode connectionMode = ConnectionMode.SOCKET;

    @JsonProperty("socket")
    private SocketSettings socketSettings = new SocketSettings();

    @JsonProperty("listener")
    private ListenerSettings listenerSettings = new ListenerSettings();

    public ConnectionSettings() {
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
