package it.vincenzopio.minestore.api.service;

import it.vincenzopio.minestore.api.MineStore;

public abstract class Service {

    protected final MineStore<?> mineStore;

    protected Service(MineStore<?> mineStore) {
        this.mineStore = mineStore;
    }

    protected abstract void onLoad();
    protected abstract void onUnload();

    public final void load(){
        onLoad();
    }

    public final void unload(){
        onUnload();
    }

}
