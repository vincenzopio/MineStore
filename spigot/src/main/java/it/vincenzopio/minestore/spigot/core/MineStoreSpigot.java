package it.vincenzopio.minestore.spigot.core;

import it.vincenzopio.minestore.api.MineStore;
import it.vincenzopio.minestore.api.server.command.CommandService;
import it.vincenzopio.minestore.api.server.platform.Platform;
import it.vincenzopio.minestore.api.server.scheduler.TaskScheduler;
import it.vincenzopio.minestore.api.server.user.PlayerResolver;
import it.vincenzopio.minestore.spigot.core.server.command.SpigotCommandService;
import it.vincenzopio.minestore.spigot.core.server.task.SpigotTaskScheduler;
import it.vincenzopio.minestore.spigot.core.server.user.SpigotPlayerResolver;
import org.bukkit.Server;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.InputStream;

public class MineStoreSpigot extends MineStore<JavaPlugin, Server> {


    private TaskScheduler taskScheduler;
    private CommandService commandService;
    private PlayerResolver playerResolver;

    public MineStoreSpigot(JavaPlugin javaPlugin) {
        super(javaPlugin, Platform.SPIGOT, javaPlugin.getServer(), javaPlugin.getDataFolder());
    }


    @Override
    protected void onLoad() {
        this.taskScheduler = new SpigotTaskScheduler(pluginInstance);
        this.commandService = new SpigotCommandService(pluginInstance, this);
        this.playerResolver = new SpigotPlayerResolver(pluginInstance);


    }

    @Override
    protected void onDisable() {

    }

    @Override
    public void forceShutdown() {
        server.shutdown();
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
        return pluginInstance.getResource(fileName);
    }
}
