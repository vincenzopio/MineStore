package it.vincenzopio.minestore.api.settings.store;

public class StoreSettings {

    private final String address;
    private final String apiKey;

    public StoreSettings(String address, String apiKey) {
        this.address = address;
        this.apiKey = apiKey;
    }

    public String getAddress() {
        return address;
    }

    public String getApiKey() {
        return apiKey;
    }
}
