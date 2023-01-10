package it.vincenzopio.minestore.api.server.scheduler;

public interface TaskScheduler {


    int async(Runnable task);
    int asyncLater(Runnable task, int time);
    int asyncTimer(Runnable task, int time, int delay);

    int sync(Runnable task);
    int later(Runnable task, int time);
    int timer(Runnable task, int time, int delay);

}
