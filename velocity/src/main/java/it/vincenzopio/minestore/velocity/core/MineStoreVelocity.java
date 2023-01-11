package it.vincenzopio.minestore.velocity.core;

import com.velocitypowered.api.proxy.ProxyServer;
import it.vincenzopio.minestore.api.MineStore;
import it.vincenzopio.minestore.api.server.command.CommandService;
import it.vincenzopio.minestore.api.server.platform.Platform;
import it.vincenzopio.minestore.api.server.scheduler.TaskScheduler;
import it.vincenzopio.minestore.api.server.user.PlayerResolver;
import it.vincenzopio.minestore.api.settings.SettingsService;
import it.vincenzopio.minestore.velocity.core.server.command.VelocityCommandService;
import it.vincenzopio.minestore.velocity.core.server.task.VelocityTaskScheduler;
import it.vincenzopio.minestore.velocity.core.server.user.VelocityPlayerResolver;

import java.io.File;
import java.io.InputStream;

public class MineStoreVelocity extends MineStore<Object, ProxyServer> {

    private TaskScheduler taskScheduler;
    private CommandService commandService;
    private PlayerResolver playerResolver;

    public MineStoreVelocity(Object pluginInstance, ProxyServer server, File dataFolder) {
        super(pluginInstance, Platform.VELOCITY, server, dataFolder);
    }


    @Override
    public void onLoad() {
        this.taskScheduler = new VelocityTaskScheduler(pluginInstance, server);
        this.playerResolver = new VelocityPlayerResolver(server);

        this.commandService = new VelocityCommandService(this);
        this.commandService.load();


    }

    @Override
    protected void onDisable() {

    }

    @Override
    public void forceShutdown() {
        System.exit(0);
    }

    @Override
    public SettingsService getSettingsService() {
        return null;
    }

    @Override
    public TaskScheduler getTaskScheduler() {
        return taskScheduler;
    }

    @Override
    public CommandService getCommandService() {
        return commandService;
    }

    @Override
    public PlayerResolver getPlayerResolver() {
        return playerResolver;
    }

    @Override
    public InputStream getResource(String fileName) {
        return null;
    }
}
