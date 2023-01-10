package it.vincenzopio.minestore.api.web.handler.socket.netty;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import it.vincenzopio.minestore.api.MineStore;
import it.vincenzopio.minestore.api.server.command.CommandDispatcher;
import it.vincenzopio.minestore.api.web.handler.socket.objects.SocketMessage;

public class SocketInboundHandler extends SimpleChannelInboundHandler<String> {

    private final MineStore<?> mineStore;
    private final CommandDispatcher commandDispatcher;
    private static final Gson GSON = new GsonBuilder().create();

    public SocketInboundHandler(MineStore<?> mineStore) {
        this.mineStore = mineStore;
        this.commandDispatcher = mineStore.getCommandDispatcher();

    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) {
        SocketMessage socketMessage = GSON.fromJson(msg, SocketMessage.class);

        String username = socketMessage.getUsername();
        String command = socketMessage.getCommand().replaceFirst("/", "");

        CommandDispatcher dispatcher = mineStore.getCommandDispatcher();

        // Check for password.



        if(socketMessage.isRequiredOnline()){
            if(mineStore.getPlayerResolver().isOnline(username)){
                dispatcher.dispatchCommand(command);
                return;
            }

            dispatcher.dispatchOnJoin(username, command);
            return;
        }

        dispatcher.dispatchCommand(command);
    }
}
