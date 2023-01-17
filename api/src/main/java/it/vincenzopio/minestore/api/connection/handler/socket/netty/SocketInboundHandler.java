package it.vincenzopio.minestore.api.connection.handler.socket.netty;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import it.vincenzopio.minestore.api.MineStore;
import it.vincenzopio.minestore.api.connection.handler.socket.netty.message.SocketMessage;
import it.vincenzopio.minestore.api.server.command.CommandExecution;
import it.vincenzopio.minestore.api.server.command.CommandService;

import java.util.logging.Level;

public class SocketInboundHandler extends SimpleChannelInboundHandler<String> {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private final MineStore<?, ?> mineStore;
    private final CommandService commandService;

    public SocketInboundHandler(MineStore<?, ?> mineStore) {
        this.mineStore = mineStore;
        this.commandService = mineStore.getCommandService();

    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) {
        try {
            SocketMessage socketMessage = OBJECT_MAPPER.readValue(msg, SocketMessage.class);

            String username = socketMessage.getUsername();
            String command = socketMessage.getCommand().replaceFirst("/", "").replaceFirst(" {3}", " ");

            if (!socketMessage.getPassword().equals(mineStore.getSettingsService().getPluginSettings().getConnectionSettings().getSocketConfig().getPassword())) {
                return;
            }

            if (socketMessage.isRequiredOnline()) {
                if (mineStore.getPlayerResolver().isOnline(username)) {
                    commandService.dispatchCommand(command);
                    return;
                }

                commandService.dispatchOnJoin(username, new CommandExecution(username, command));
                return;
            }

            commandService.dispatchCommand(command);
        } catch (JsonProcessingException e) {
            MineStore.LOGGER.log(Level.SEVERE, "An error occurred trying to read a socket message: ", e);
        }
    }
}
