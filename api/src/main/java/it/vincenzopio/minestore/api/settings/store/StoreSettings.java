package it.vincenzopio.minestore.api.settings.store;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StoreSettings {

    @JsonProperty("address")
    private String address = "address";
    @JsonProperty("api-key")
    private String apiKey = "apikey";

    public StoreSettings() {
    }


    public String getApiAddress() {
        return address.endsWith("/") ? address : address + "/";
    }

    public String getApiKey() {
        return apiKey;
    }
}
