package dev.taah.pingu.packets.status;

import dev.taah.pingu.handler.PacketBuffer;
import dev.taah.pingu.packets.AbstractPacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ClientboundPingPacket extends AbstractPacket
{

    private final long payload;

    @Override
    public void deserialize(ChannelHandlerContext channel, PacketBuffer buf)
    {
    }

    @Override
    public ByteBuf serialize(ChannelHandlerContext channel)
    {
        PacketBuffer buffer = new PacketBuffer();
        buffer.writeVarInt(0x01);
        buffer.writeLong(this.payload);
        return buffer;
    }
}
