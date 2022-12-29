package dev.taah.pingu.packets.play;

import dev.taah.pingu.handler.PacketBuffer;
import dev.taah.pingu.packets.AbstractPacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ClientboundRecipesPacket extends AbstractPacket
{

    @Override
    public void deserialize(ChannelHandlerContext channel, PacketBuffer buf)
    {

    }

    @Override
    public ByteBuf serialize(ChannelHandlerContext channel)
    {

        final PacketBuffer buffer = new PacketBuffer();

        buffer.writeVarInt(0x69);
        buffer.writeVarInt(0);

        return buffer;
    }
}
