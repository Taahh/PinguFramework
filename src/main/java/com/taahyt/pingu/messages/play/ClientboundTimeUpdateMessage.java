package com.taahyt.pingu.messages.play;

import com.taahyt.pingu.messages.AbstractMessage;
import com.taahyt.pingu.util.PacketBuffer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public class ClientboundTimeUpdateMessage extends AbstractMessage
{
    public ClientboundTimeUpdateMessage()
    {
        super(0x59);
    }

    @Override
    public void deserialize(ChannelHandlerContext channel, PacketBuffer buf)
    {

    }

    @Override
    public ByteBuf serialize(ChannelHandlerContext channel)
    {
        PacketBuffer buffer = new PacketBuffer();
        buffer.writeVarInt(this.getPacketId());
        buffer.writeLong(20);
        buffer.writeLong(20);
        return buffer;
    }
}
