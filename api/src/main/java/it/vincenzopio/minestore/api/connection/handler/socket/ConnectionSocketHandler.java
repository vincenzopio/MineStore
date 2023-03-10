package it.vincenzopio.minestore.api.connection.handler.socket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import it.vincenzopio.minestore.api.MineStore;
import it.vincenzopio.minestore.api.connection.handler.ConnectionHandler;
import it.vincenzopio.minestore.api.connection.handler.socket.netty.SocketChannelInitializer;

import java.util.logging.Level;

public class ConnectionSocketHandler implements ConnectionHandler {

    private final MineStore<?, ?> mineStore;

    private final ServerBootstrap serverBootstrap;

    public ConnectionSocketHandler(MineStore<?, ?> mineStore) {
        this.mineStore = mineStore;

        EventLoopGroup parent = new NioEventLoopGroup();
        EventLoopGroup child = new NioEventLoopGroup();

        serverBootstrap = new ServerBootstrap()
                .group(parent, child)
                .channel(NioServerSocketChannel.class)
                .childHandler(new SocketChannelInitializer(mineStore))
                .childOption(ChannelOption.SO_KEEPALIVE, true);
    }

    @Override
    public void connect() {
        int port = mineStore.getSettingsService().getPluginSettings().getConnectionSettings().getSocketConfig().getPort();
        try {
            serverBootstrap.bind(port).sync();
        } catch (Exception ex) {
            MineStore.LOGGER.log(Level.SEVERE, ex, () -> "Could not connect to server, submit this error:");
            mineStore.forceShutdown();
        }
    }

    @Override
    public void disconnect() {

    }
}
