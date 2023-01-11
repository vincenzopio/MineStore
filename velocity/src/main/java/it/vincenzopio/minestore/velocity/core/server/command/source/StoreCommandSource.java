package it.vincenzopio.minestore.velocity.core.server.command.source;

import com.velocitypowered.api.permission.Tristate;
import com.velocitypowered.api.proxy.ConsoleCommandSource;

public class StoreCommandSource implements ConsoleCommandSource {
    @Override
    public Tristate getPermissionValue(String s) {
        return Tristate.TRUE;
    }
}
