package com.taahyt.pingu.messages.status;

import com.taahyt.pingu.messages.AbstractMessage;
import com.taahyt.pingu.util.PacketBuffer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public class ClientboundPingMessage extends AbstractMessage
{
    public long payload;

    public ClientboundPingMessage()
    {
        super(0x01);
    }

    @Override
    public void deserialize(ChannelHandlerContext channel, PacketBuffer buf)
    {
    }

    @Override
    public ByteBuf serialize(ChannelHandlerContext channel)
    {
        PacketBuffer buffer = new PacketBuffer();
        buffer.writeVarInt(0x01);
        buffer.writeLong(payload);
        /*int length = buffer.getByteArray().length;
        buffer.resetWriterIndex();
        buffer.writeVarInt(length);*/
        return buffer;
    }
}
