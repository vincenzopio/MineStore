package it.vincenzopio.minestore.api.settings;

import it.vincenzopio.minestore.api.settings.connection.ConnectionSettings;
import it.vincenzopio.minestore.api.settings.store.StoreSettings;

public class PluginSettings {

    private final StoreSettings storeSettings;
    private final ConnectionSettings connectionSettings;

    public PluginSettings(StoreSettings storeSettings, ConnectionSettings connectionSettings) {
        this.storeSettings = storeSettings;
        this.connectionSettings = connectionSettings;
    }

    public ConnectionSettings getConnectionSettings() {
        return connectionSettings;
    }

    public StoreSettings getStoreSettings() {
        return storeSettings;
    }
}
