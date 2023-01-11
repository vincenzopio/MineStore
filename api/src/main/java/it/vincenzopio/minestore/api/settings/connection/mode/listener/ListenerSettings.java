package it.vincenzopio.minestore.api.settings.connection.mode.listener;

public class ListenerSettings {

    private final String secretKey;

    public ListenerSettings(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getSecretKey() {
        return secretKey;
    }
}
