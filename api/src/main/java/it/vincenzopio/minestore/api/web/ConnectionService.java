package it.vincenzopio.minestore.api.web;

import it.vincenzopio.minestore.api.MineStore;
import it.vincenzopio.minestore.api.service.Service;

public class ConnectionService extends Service {
    protected ConnectionService(MineStore<?> mineStore) {
        super(mineStore);
    }

    @Override
    protected void onLoad() {

    }

    @Override
    protected void onUnload() {

    }
}
