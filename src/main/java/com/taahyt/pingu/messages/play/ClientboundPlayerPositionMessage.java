package com.taahyt.pingu.messages.play;

import com.taahyt.pingu.messages.AbstractMessage;
import com.taahyt.pingu.util.packet.PacketBuffer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public class ClientboundPlayerPositionMessage extends AbstractMessage
{
    public final int teleportId;
    public ClientboundPlayerPositionMessage(int teleportId)
    {
        super(0x38);
        this.teleportId = teleportId;
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
        buffer.writeDouble(0);
        buffer.writeDouble(64);
        buffer.writeDouble(0);
        buffer.writeFloat(0);
        buffer.writeFloat(0);
        buffer.writeByte(0);
        buffer.writeVarInt(this.teleportId);
        buffer.writeBoolean(false);
        return buffer;
    }
}
