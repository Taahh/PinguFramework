package dev.taah.pingu.packets.status;

import dev.taah.pingu.handler.PacketBuffer;
import dev.taah.pingu.packets.AbstractPacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public class ServerboundPingPacket extends AbstractPacket
{

    private long payload;

    @Override
    public void deserialize(ChannelHandlerContext channel, PacketBuffer buf)
    {
        this.payload = buf.readLong();
    }

    @Override
    public ByteBuf serialize(ChannelHandlerContext channel)
    {
        return new ClientboundPingPacket(this.payload).serialize(channel);
    }
}
