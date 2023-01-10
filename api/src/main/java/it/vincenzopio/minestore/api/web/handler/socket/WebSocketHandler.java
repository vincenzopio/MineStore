package it.vincenzopio.minestore.api.web.handler.socket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import it.vincenzopio.minestore.api.MineStore;
import it.vincenzopio.minestore.api.web.handler.ConnectionHandler;
import it.vincenzopio.minestore.api.web.handler.socket.netty.SocketChannelInitializer;

import java.util.logging.Level;

public class WebSocketHandler implements ConnectionHandler {


    private final MineStore<?> mineStore;

    private final ServerBootstrap serverBootstrap;

    public WebSocketHandler(MineStore<?> mineStore) {
        this.mineStore = mineStore;

        EventLoopGroup parent = new NioEventLoopGroup();
        EventLoopGroup child = new NioEventLoopGroup();

        serverBootstrap = new ServerBootstrap()
                .group(parent, child)
                .channel(NioServerSocketChannel.class)
                .childHandler(new SocketChannelInitializer())
                .childOption(ChannelOption.SO_KEEPALIVE, true);
    }

    @Override
    public void connect() {
        int port = 0;
        try {
            serverBootstrap.bind(port).sync();
        } catch (Exception ex) {
            MineStore.LOGGER.log(Level.SEVERE, ex, () -> "Could not connect to server, submit this error:");
            mineStore.end();
        }
    }
}
