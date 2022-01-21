package com.taahyt.pingu.messages.login;

import com.taahyt.pingu.messages.AbstractMessage;
import com.taahyt.pingu.messages.play.ClientboundJoinGameMessage;
import com.taahyt.pingu.util.packet.PacketBuffer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.SneakyThrows;

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

        ClientboundLoginSuccessMessage message = new ClientboundLoginSuccessMessage();
        message.username = name;
        channel.writeAndFlush(new ClientboundSetCompressionMessage().serialize(channel)).sync();
        channel.writeAndFlush(message.serialize(channel)).sync();
        channel.writeAndFlush(new ClientboundJoinGameMessage().serialize(channel));
    }

    @Override
    public ByteBuf serialize(ChannelHandlerContext channel)
    {
        return null;
    }
}
