package com.taahyt.pingu.messages.login;

import com.taahyt.pingu.messages.AbstractMessage;
import com.taahyt.pingu.util.packet.PacketBuffer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public class ClientboundSetCompressionMessage extends AbstractMessage
{
    public ClientboundSetCompressionMessage()
    {
        super(0x03);
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
        buffer.writeVarInt(-1);
        return buffer;
    }
}
