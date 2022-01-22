package com.taahyt.pingu.messages.play;

import com.taahyt.pingu.messages.AbstractMessage;
import com.taahyt.pingu.util.packet.PacketBuffer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public class ClientboundPlayerInfoMessage extends AbstractMessage
{
    public ClientboundPlayerInfoMessage()
    {
        super(0x36);
    }

    @Override
    public void deserialize(ChannelHandlerContext channel, PacketBuffer buf)
    {

    }

    @Override
    public ByteBuf serialize(ChannelHandlerContext channel)
    {
        System.out.println("Position");
        PacketBuffer buffer = new PacketBuffer();
        buffer.writeVarInt(this.getPacketId());
        buffer.writeVarInt(0);
        buffer.writeVarInt(1);
        buffer.writeString("Taahh");
        buffer.writeVarInt(0);
        buffer.writeVarInt(0);
        buffer.writeVarInt(150);
        buffer.writeBoolean(false);
        return buffer;
    }
}
