package it.vincenzopio.minestore.velocity.core.server.command;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.event.PostOrder;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.PostLoginEvent;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import it.vincenzopio.minestore.api.server.command.CommandExecution;
import it.vincenzopio.minestore.api.server.command.CommandService;
import it.vincenzopio.minestore.velocity.core.MineStoreVelocity;
import it.vincenzopio.minestore.velocity.core.server.command.source.StoreCommandSource;

import java.util.List;
import java.util.Locale;

public class VelocityCommandService extends CommandService {

    private final ProxyServer proxyServer;
    private final CommandSource commandSource;

    public VelocityCommandService(MineStoreVelocity mineStore) {
        super(mineStore);

        this.proxyServer = mineStore.getServer();
        this.commandSource = new StoreCommandSource();
    }


    @Override
    protected void onLoad() {
        proxyServer.getEventManager().register(mineStore.getPluginInstance(), this);
    }

    @Override
    protected void onUnload() {
        // Do nothing.
    }

    @Override
    public void dispatchCommand(String command) {
        proxyServer.getCommandManager().executeAsync(commandSource, command);
    }

    @Subscribe(order = PostOrder.LAST)
    public void playerJoinEvent(PostLoginEvent event) {
        Player player = event.getPlayer();

        String playerName = player.getUsername().toLowerCase(Locale.ROOT);

        if (!ONLINE_COMMANDS.containsKey(playerName)) return;

        List<CommandExecution> commands = ONLINE_COMMANDS.get(playerName);

        commands.forEach(commandExecution -> dispatchCommand(commandExecution.getCommand()));

        ONLINE_COMMANDS.remove(player.getUsername());

        saveCache();
    }

}
