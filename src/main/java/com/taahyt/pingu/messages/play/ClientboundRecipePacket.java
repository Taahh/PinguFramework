package com.taahyt.pingu.messages.play;

import com.taahyt.pingu.messages.AbstractMessage;
import com.taahyt.pingu.util.PacketBuffer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public class ClientboundRecipePacket extends AbstractMessage
{
    public ClientboundRecipePacket()
    {
        super(0x66);
    }

    @Override
    public void deserialize(ChannelHandlerContext channel, PacketBuffer buf)
    {

    }

    @Override
    public ByteBuf serialize(ChannelHandlerContext channel)
    {
        System.out.println("Recipe");
        PacketBuffer buffer = new PacketBuffer();
        buffer.writeVarInt(this.getPacketId());
        buffer.writeVarInt(0);
        return buffer;
    }
}
