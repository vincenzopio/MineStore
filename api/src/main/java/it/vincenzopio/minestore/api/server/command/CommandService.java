package it.vincenzopio.minestore.api.server.command;

import it.vincenzopio.minestore.api.MineStore;
import it.vincenzopio.minestore.api.service.Service;

public abstract class CommandService extends Service {

    protected CommandService(MineStore<?, ?> mineStore) {
        super(mineStore);
    }

    public abstract void dispatchCommand(String command);

    public abstract void dispatchOnJoin(String username, CommandExecution command);


}
