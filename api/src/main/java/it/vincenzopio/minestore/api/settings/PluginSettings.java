package it.vincenzopio.minestore.api.settings;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.vincenzopio.minestore.api.settings.connection.ConnectionSettings;
import it.vincenzopio.minestore.api.settings.store.StoreSettings;


public class PluginSettings {


    @JsonProperty("store-api")
    private StoreSettings storeSettings = new StoreSettings();
    @JsonProperty("store-connection")
    private ConnectionSettings connectionSettings = new ConnectionSettings();

    public PluginSettings() {
    }


    public ConnectionSettings getConnectionSettings() {
        return connectionSettings;
    }

    public StoreSettings getStoreSettings() {
        return storeSettings;
    }
}
