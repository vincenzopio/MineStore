package it.vincenzopio.minestore.api;

import it.vincenzopio.minestore.api.server.command.CommandDispatcher;
import it.vincenzopio.minestore.api.server.platform.Platform;
import it.vincenzopio.minestore.api.server.scheduler.TaskScheduler;
import it.vincenzopio.minestore.api.server.user.PlayerResolver;

import java.io.File;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public abstract class MineStore<T> {

    public static final Logger LOGGER = LogManager.getLogManager().getLogger("MineStore");


    protected final Object pluginInstance;
    protected final Platform platform;
    protected final T server;
    protected final File dataFolder;

    protected MineStore(Object pluginObject, Platform platform, T serverInstance, File dataFolder) {
        this.pluginInstance = pluginObject;
        this.platform = platform;
        this.server = serverInstance;
        this.dataFolder = dataFolder;


        LOGGER.info(() -> "Loading MineStore for Platform " + platform.name());
        LOGGER.info(() -> "Coded with <3 by @vincenzopio.");
    }

    public abstract TaskScheduler getTaskScheduler();
    public abstract CommandDispatcher getCommandDispatcher();
    public abstract PlayerResolver getPlayerResolver();

    public abstract void initialize();

    protected abstract void shutdown();

    public void end(){
        shutdown();
    }

    public Platform getPlatform() {
        return platform;
    }

    public Object getPluginInstance() {
        return pluginInstance;
    }

    public T getServer() {
        return server;
    }
}

