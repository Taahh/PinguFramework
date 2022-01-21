package com.taahyt.pingu.messages.status;

import com.taahyt.pingu.messages.AbstractMessage;
import com.taahyt.pingu.messages.Messages;
import com.taahyt.pingu.util.packet.PacketBuffer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public class ServerboundPingMessage extends AbstractMessage
{
    public ServerboundPingMessage()
    {
        super(0x01);
    }

    @Override
    public void deserialize(ChannelHandlerContext channel, PacketBuffer buf)
    {
        long payload = buf.readLong();
        ClientboundPingMessage pongMessage = (ClientboundPingMessage) Messages.PONG.getMessage();
        pongMessage.payload = payload;
        channel.writeAndFlush(pongMessage.serialize(channel));
    }

    @Override
    public ByteBuf serialize(ChannelHandlerContext channel)
    {
        return null;
    }
}
