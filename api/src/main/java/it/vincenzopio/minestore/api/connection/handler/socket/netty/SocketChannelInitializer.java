package it.vincenzopio.minestore.api.connection.handler.socket.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import it.vincenzopio.minestore.api.MineStore;

public class SocketChannelInitializer extends ChannelInitializer<SocketChannel> {

    private final MineStore<?, ?> mineStore;

    public SocketChannelInitializer(MineStore<?, ?> mineStore) {
        this.mineStore = mineStore;
    }

    @Override
    protected void initChannel(SocketChannel socketChannel) {
        socketChannel.pipeline().addLast(new StringEncoder(), new StringDecoder());

        socketChannel.pipeline().addLast(new SocketInboundHandler(mineStore));
    }
}
