package dev.taah.pingu.packets.play;

import dev.taah.pingu.handler.PacketBuffer;
import dev.taah.pingu.packets.AbstractPacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public class ClientboundPluginMessagePacket extends AbstractPacket
{

    @Override
    public void deserialize(ChannelHandlerContext channel, PacketBuffer buf)
    {

    }

    @Override
    public ByteBuf serialize(ChannelHandlerContext channel)
    {
        final PacketBuffer buffer = new PacketBuffer();

        buffer.writeVarInt(0x15);
        buffer.writeString("minecraft:brand");
        buffer.writeByteArray("Pingu Server".getBytes());

        return buffer;
    }
}
