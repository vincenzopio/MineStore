package it.vincenzopio.minestore.api.connection.handler.socket.netty;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import it.vincenzopio.minestore.api.MineStore;
import it.vincenzopio.minestore.api.connection.handler.socket.netty.message.SocketMessage;
import it.vincenzopio.minestore.api.server.command.CommandService;

public class SocketInboundHandler extends SimpleChannelInboundHandler<String> {

    private static final Gson GSON = new GsonBuilder().create();
    private final MineStore<?, ?> mineStore;
    private final CommandService commandService;

    public SocketInboundHandler(MineStore<?, ?> mineStore) {
        this.mineStore = mineStore;
        this.commandService = mineStore.getCommandService();

    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) {
        SocketMessage socketMessage = GSON.fromJson(msg, SocketMessage.class);

        String username = socketMessage.getUsername();
        String command = socketMessage.getCommand().replaceFirst("/", "").replaceFirst(" {3}", " ");

        if(!socketMessage.getPassword().equals(mineStore.getSettingsService().getPluginSettings().getConnectionSettings().getSocketConfig().getPassword())){
            return;
        }

        if (socketMessage.isRequiredOnline()) {
            if (mineStore.getPlayerResolver().isOnline(username)) {
                commandService.dispatchCommand(command);
                return;
            }

            commandService.dispatchOnJoin(username, command);
            return;
        }

        commandService.dispatchCommand(command);
    }
}
