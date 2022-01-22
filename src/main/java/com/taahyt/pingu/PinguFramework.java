package com.taahyt.pingu;

import com.taahyt.pingu.messages.play.ClientboundTimeUpdateMessage;
import com.taahyt.pingu.handler.PacketDecoder;
import com.taahyt.pingu.server.Server;
import com.taahyt.pingu.util.EnumUtil;
import com.taahyt.pingu.util.Status;
import com.taahyt.pingu.util.packet.Varint21FrameDecoder;
import com.taahyt.pingu.util.packet.Varint21LengthFieldPrepender;
import com.taahyt.pingu.util.registry.Registries;
import com.taahyt.pingu.util.tags.TagUtil;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;
import lombok.Getter;
import lombok.SneakyThrows;

import java.io.File;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class PinguFramework
{
    @Getter
    private static final Server server = new Server();

    public static final Map<ChannelHandlerContext, Status> CLIENTS = new HashMap<>();

    @SneakyThrows
    public static void main(String[] args)
    {
        Registries.registerAll(new File("./generated/reports/registries.json"));
        File blockTagsDir = new File("./generated/data/minecraft/tags/blocks");
        File itemTagsDir = new File("./generated/data/minecraft/tags/items");
        File entityTypesDir = new File("./generated/data/minecraft/tags/entity_types");
        File fluidsDir = new File("./generated/data/minecraft/tags/fluids");
        File gameEventsDir = new File("./generated/data/minecraft/tags/game_events");

        TagUtil.loadAllBlocks(blockTagsDir);
        TagUtil.loadAllItems(itemTagsDir);
        TagUtil.loadAllEntities(entityTypesDir);
        TagUtil.loadAllFluids(fluidsDir);
        TagUtil.loadAllGameEvents(gameEventsDir);

        TagUtil.fixedQueues();

        EnumUtil.generateEnumMaterial(new File("./generated/Material.txt"));
        EnumUtil.generateEnumEntityType(new File("./generated/EntityType.txt"));
        EnumUtil.generateEnumGameEvents(new File("./generated/GameEvents.txt"));
        EnumUtil.generateEnumFluids(new File("./generated/Fluids.txt"));

        System.out.println("Loaded " + TagUtil.BLOCK_TAGS.size() + " block tags!");
        System.out.println("Loaded " + TagUtil.ITEM_TAGS.size() + " item tags!");
        System.out.println("Loaded " + TagUtil.ENTITY_TAGS.size() + " entity tags!");
        System.out.println("Loaded " + TagUtil.FLUIDS.size() + " fluid tags!");
        System.out.println("Loaded " + TagUtil.GAME_EVENTS.size() + " game events tags!");

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
                    socketChannel.pipeline().
                            addLast("timeout", new ReadTimeoutHandler(30)).
                            addLast("splitter", new Varint21FrameDecoder()).
                            addLast("prepender", new Varint21LengthFieldPrepender()).
                            addLast("decoder", new PacketDecoder());
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
