package it.vincenzopio.minestore.api.config;

import it.vincenzopio.minestore.api.config.database.DatabaseConfig;

public class PluginConfig {

    private final DatabaseConfig databaseConfig;

    public PluginConfig(DatabaseConfig databaseConfig) {
        this.databaseConfig = databaseConfig;
    }
}
