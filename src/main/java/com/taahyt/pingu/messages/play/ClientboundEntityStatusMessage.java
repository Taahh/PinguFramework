package com.taahyt.pingu.messages.play;

import com.taahyt.pingu.messages.AbstractMessage;
import com.taahyt.pingu.util.packet.PacketBuffer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public class ClientboundEntityStatusMessage extends AbstractMessage
{
    private final int entityId;
    public ClientboundEntityStatusMessage(int entityId)
    {
        super(0x1B);
        this.entityId = entityId;
    }

    @Override
    public void deserialize(ChannelHandlerContext channel, PacketBuffer buf)
    {

    }

    @Override
    public ByteBuf serialize(ChannelHandlerContext channel)
    {
        System.out.println("Entity Status");
        PacketBuffer buffer = new PacketBuffer();
        buffer.writeVarInt(this.getPacketId());
        buffer.writeInt(this.entityId);
        buffer.writeByte(28);
        return buffer;
    }
}
