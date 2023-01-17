package it.vincenzopio.minestore.api.connection;

import it.vincenzopio.minestore.api.MineStore;
import it.vincenzopio.minestore.api.connection.handler.ConnectionHandler;
import it.vincenzopio.minestore.api.connection.handler.listener.ConnectionListenerHandler;
import it.vincenzopio.minestore.api.connection.handler.socket.ConnectionSocketHandler;
import it.vincenzopio.minestore.api.service.Service;
import it.vincenzopio.minestore.api.settings.connection.ConnectionSettings;
import it.vincenzopio.minestore.api.settings.connection.mode.ConnectionMode;

public class ConnectionService extends Service {

    private ConnectionHandler connectionHandler;

    public ConnectionService(MineStore<?, ?> mineStore) {
        super(mineStore);
    }

    @Override
    protected void onLoad() {
        ConnectionSettings connectionSettings = mineStore.getSettingsService().getPluginSettings().getConnectionSettings();

        if (connectionSettings.getConnectionMode() == ConnectionMode.LISTENER)
            connectionHandler = new ConnectionListenerHandler(mineStore);
        else
            connectionHandler = new ConnectionSocketHandler(mineStore);


        MineStore.LOGGER.info("Connecting to " + mineStore.getSettingsService().getPluginSettings().getStoreSettings().getApiAddress() + " with method: " + connectionSettings.getConnectionMode());

        connectionHandler.connect();
    }

    @Override
    protected void onUnload() {
        connectionHandler.disconnect();
    }
}
