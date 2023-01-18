package it.vincenzopio.minestore.api.settings.connection.mode.listener;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ListenerSettings {

    @JsonProperty("secret_key")
    private String secretKey = "secret_key";

    @JsonProperty("update_delay")
    private int updateDelay = 5;

    public ListenerSettings() {
    }


    public String getSecretKey() {
        return secretKey;
    }

    public int getUpdateDelay() {
        return updateDelay;
    }
}
