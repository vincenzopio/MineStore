package it.vincenzopio.minestore.api.server.scheduler;

public interface TaskScheduler {


    int async(Runnable task);

    int asyncLater(Runnable task, int time);

    int asyncTimer(Runnable task, int time, int delay);

}
