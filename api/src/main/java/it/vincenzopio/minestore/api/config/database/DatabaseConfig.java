package it.vincenzopio.minestore.api.config.database;

public class DatabaseConfig {


    private final boolean enabled;
    private final String address, username, password, database;
    private final int port;

    public DatabaseConfig(boolean enabled, String address, String username, String password, String database, int port) {
        this.enabled = enabled;
        this.address = address;
        this.username = username;
        this.password = password;
        this.database = database;
        this.port = port;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public int getPort() {
        return port;
    }

    public String getAddress() {
        return address;
    }

    public String getDatabase() {
        return database;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }
}


