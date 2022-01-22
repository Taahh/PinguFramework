package com.taahyt.pingu.messages.login;

import com.taahyt.pingu.PinguFramework;
import com.taahyt.pingu.messages.AbstractMessage;
import com.taahyt.pingu.messages.play.ClientboundJoinGameMessage;
import com.taahyt.pingu.player.Player;
import com.taahyt.pingu.server.gamemode.GameMode;
import com.taahyt.pingu.util.packet.PacketBuffer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.SneakyThrows;

import java.net.InetSocketAddress;

public class ServerboundLoginStartMessage extends AbstractMessage
{
    public ServerboundLoginStartMessage()
    {
        super(0x00);
    }

    @SneakyThrows
    @Override
    public void deserialize(ChannelHandlerContext channel, PacketBuffer buf)
    {
        final String name = buf.readString();
        System.out.println("Player Connecting: " + name);

        channel.writeAndFlush(new ClientboundSetCompressionMessage().serialize(channel)).sync();
        channel.writeAndFlush(new ClientboundLoginSuccessMessage(name).serialize(channel)).sync();
        Player player = PinguFramework.getServer().getPlayer((InetSocketAddress) channel.channel().remoteAddress());
        if (player != null)
        {
            channel.writeAndFlush(new ClientboundJoinGameMessage(GameMode.CREATIVE, player).serialize(channel));
        }
    }

    @Override
    public ByteBuf serialize(ChannelHandlerContext channel)
    {
        return null;
    }
}
