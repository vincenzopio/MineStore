package it.vincenzopio.minestore.api.settings.connection.mode.listener;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ListenerSettings {

    @JsonProperty("secret_key")
    private String secretKey = "secret_key";

    public ListenerSettings() {
    }


    public String getSecretKey() {
        return secretKey;
    }
}
