package com.taahyt.pingu.messages.status;

import com.taahyt.pingu.messages.AbstractMessage;
import com.taahyt.pingu.messages.Messages;
import com.taahyt.pingu.util.packet.PacketBuffer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.SneakyThrows;

public class ServerboundRequestMessage extends AbstractMessage
{
    public ServerboundRequestMessage()
    {
        super(0x00);
    }

    @SneakyThrows
    @Override
    public void deserialize(ChannelHandlerContext channel, PacketBuffer buf)
    {
        System.out.println("Deserializing Request");

        ClientboundRequestMessage message = (ClientboundRequestMessage) Messages.RESPONSE.getMessage();
        channel.writeAndFlush(message.serialize(channel)).sync();
    }

    @Override
    public ByteBuf serialize(ChannelHandlerContext channel)
    {
        return null;
    }
}
