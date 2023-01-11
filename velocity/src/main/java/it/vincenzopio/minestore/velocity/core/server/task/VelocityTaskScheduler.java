package it.vincenzopio.minestore.velocity.core.server.task;

import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.scheduler.Scheduler;
import it.vincenzopio.minestore.api.server.scheduler.TaskScheduler;

import java.time.Duration;

public class VelocityTaskScheduler implements TaskScheduler {

    private final Object pluginObject;

    private final ProxyServer proxyServer;
    private final Scheduler scheduler;

    public VelocityTaskScheduler(Object pluginObject, ProxyServer proxyServer) {
        this.pluginObject = pluginObject;
        this.proxyServer = proxyServer;
        this.scheduler = proxyServer.getScheduler();
    }


    @Override
    public int async(Runnable task) {
        Scheduler.TaskBuilder taskBuilder = proxyServer.getScheduler().buildTask(pluginObject, task);

        return taskBuilder.schedule().hashCode();
    }

    @Override
    public int asyncLater(Runnable task, int time) {
        Scheduler.TaskBuilder taskBuilder = proxyServer.getScheduler().buildTask(pluginObject, task);

        taskBuilder.delay(Duration.ofSeconds(time));

        return taskBuilder.schedule().hashCode();
    }

    @Override
    public int asyncTimer(Runnable task, int time, int delay) {
        Scheduler.TaskBuilder taskBuilder = proxyServer.getScheduler().buildTask(pluginObject, task);

        if (delay > 0) taskBuilder.delay(Duration.ofSeconds(delay));

        taskBuilder.repeat(Duration.ofSeconds(time));

        return taskBuilder.schedule().hashCode();
    }

}
