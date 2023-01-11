package it.vincenzopio.minestore.api.settings.store;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StoreSettings {

    private String address = "address";
    @JsonProperty("api-key")
    private String apiKey = "apikey";


    public StoreSettings() {}


    public String getAddress() {
        return address;
    }

    public String getApiKey() {
        return apiKey;
    }
}
