package com.taahyt.pingu.messages.play;

import com.taahyt.pingu.messages.AbstractMessage;
import com.taahyt.pingu.util.packet.PacketBuffer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public class ClientboundTagPacket extends AbstractMessage
{
    public ClientboundTagPacket()
    {
        super(0x67);
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
        return buffer;
    }

    public void doTags()
    {

    }
}
