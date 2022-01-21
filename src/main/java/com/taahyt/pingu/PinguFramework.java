package com.taahyt.pingu;

import com.taahyt.pingu.messages.play.ClientboundTimeUpdateMessage;
import com.taahyt.pingu.util.PacketDecoder;
import com.taahyt.pingu.util.Status;
import com.taahyt.pingu.util.Varint21FrameDecoder;
import com.taahyt.pingu.util.Varint21LengthFieldPrepender;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;
import lombok.SneakyThrows;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class PinguFramework
{

    public static final Map<ChannelHandlerContext, Status> CLIENTS = new HashMap<>();

    @SneakyThrows
    public static void main(String[] args)
    {
        new Timer("tick").schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                CLIENTS.keySet().stream().filter(client -> CLIENTS.get(client) == Status.PLAY).forEach(client -> {
                    System.out.println("Updated time for " + client.channel().remoteAddress());
                    client.writeAndFlush(new ClientboundTimeUpdateMessage().serialize(client));
                });
            }
        }, 0, 1000);

        EventLoopGroup group = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(group);
            serverBootstrap.channel(NioServerSocketChannel.class);
            serverBootstrap.localAddress(new InetSocketAddress("localhost", 25565));

            serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>()
            {
                protected void initChannel(SocketChannel socketChannel) throws Exception
                {
//                    socketChannel.pipeline().addLast(new Varint21FrameDecoder());
//                    socketChannel.pipeline().addLast(new Varint21LengthFieldPrepender());
                    socketChannel.pipeline().addLast("timeout", new ReadTimeoutHandler(30)).addLast("splitter", new Varint21FrameDecoder()).addLast("prepender", new Varint21LengthFieldPrepender()).addLast("decoder", new PacketDecoder())/*.addLast("encoder", new PacketEncoder(PacketFlow.SERVERBOUND)).addLast("packet_handler", connection)*/;
                    //socketChannel.pipeline().addLast(new PacketHandler());
                }
            });
            ChannelFuture channelFuture = serverBootstrap.bind().sync();
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully().sync();
        }
    }

}
