package it.vincenzopio.minestore.api;

import it.vincenzopio.minestore.api.connection.ConnectionService;
import it.vincenzopio.minestore.api.server.command.CommandService;
import it.vincenzopio.minestore.api.server.platform.Platform;
import it.vincenzopio.minestore.api.server.scheduler.TaskScheduler;
import it.vincenzopio.minestore.api.server.user.PlayerResolver;
import it.vincenzopio.minestore.api.settings.SettingsService;

import java.io.File;
import java.io.InputStream;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public abstract class MineStore<G, T> {

    public static final Logger LOGGER = LogManager.getLogManager().getLogger("MineStore");

    protected final G pluginInstance;
    protected final Platform platform;
    protected final T server;
    protected final File dataFolder;

    protected ConnectionService connectionService;
    protected SettingsService settingsService;

    protected MineStore(G pluginObject, Platform platform, T serverInstance, File dataFolder) {
        this.pluginInstance = pluginObject;
        this.platform = platform;
        this.server = serverInstance;
        this.dataFolder = dataFolder;
    }


    protected abstract void onLoad();

    protected abstract void onDisable();

    public abstract void forceShutdown();

    public final void load() {
        LOGGER.info(() -> "Loading MineStore for Platform " + platform.name());
        LOGGER.info(() -> "Coded with <3 by @vincenzopio.");

        settingsService = new SettingsService(this);
        settingsService.load();

        onLoad();

        connectionService = new ConnectionService(this);
        connectionService.load();
    }

    public final void disable() {
        LOGGER.info(() -> "Disabling MineStore for Platform " + platform.name());
        LOGGER.info(() -> "Coded with <3 by @vincenzopio.");

        connectionService.unload();

        onDisable();
    }


    public abstract TaskScheduler getTaskScheduler();

    public abstract CommandService getCommandService();

    public abstract PlayerResolver getPlayerResolver();

    public abstract InputStream getResource(String fileName);


    public SettingsService getSettingsService() {
        return settingsService;
    }

    public ConnectionService getConnectionService() {
        return connectionService;
    }

    public Platform getPlatform() {
        return platform;
    }

    public G getPluginInstance() {
        return pluginInstance;
    }

    public File getDataFolder() {
        return dataFolder;
    }


    public T getServer() {
        return server;
    }
}

