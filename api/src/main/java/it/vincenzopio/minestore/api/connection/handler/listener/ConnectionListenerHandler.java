package it.vincenzopio.minestore.api.connection.handler.listener;

import it.vincenzopio.minestore.api.MineStore;
import it.vincenzopio.minestore.api.connection.handler.ConnectionHandler;

public class ConnectionListenerHandler implements ConnectionHandler {

    private final MineStore<?, ?> mineStore;

    public ConnectionListenerHandler(MineStore<?, ?> mineStore) {
        this.mineStore = mineStore;
    }

    @Override
    public void connect() {
        mineStore.getTaskScheduler().asyncTimer(() -> {

            // Work in progress.


        }, 0, 10);

    }

    @Override
    public void disconnect() {

    }
}
