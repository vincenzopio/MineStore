package it.vincenzopio.minestore.spigot.core.server.task;


import it.vincenzopio.minestore.api.server.scheduler.TaskScheduler;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.time.Duration;

public class SpigotTaskScheduler implements TaskScheduler {

    private final JavaPlugin javaPlugin;
    private final BukkitScheduler bukkitScheduler;

    public SpigotTaskScheduler(JavaPlugin javaPlugin) {
        this.javaPlugin = javaPlugin;
        this.bukkitScheduler = javaPlugin.getServer().getScheduler();
    }


    @Override
    public int async(Runnable task) {
        return bukkitScheduler.runTaskAsynchronously(javaPlugin, task).getTaskId();
    }

    @Override
    public int asyncLater(Runnable task, int time) {
        return bukkitScheduler.runTaskLaterAsynchronously(javaPlugin, task, Duration.ofSeconds(time).toMillis()).getTaskId();
    }

    @Override
    public int asyncTimer(Runnable task, int time, int delay) {
        return bukkitScheduler.runTaskTimerAsynchronously(javaPlugin, task, delay * 20, time * 20).getTaskId();
    }
}
