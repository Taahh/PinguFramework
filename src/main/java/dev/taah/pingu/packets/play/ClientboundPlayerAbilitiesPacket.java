package dev.taah.pingu.packets.play;

import dev.taah.pingu.handler.PacketBuffer;
import dev.taah.pingu.packets.AbstractPacket;
import dev.taah.pingu.world.Location;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.RequiredArgsConstructor;

import java.util.EnumSet;
import java.util.Set;

@RequiredArgsConstructor
public class ClientboundPlayerAbilitiesPacket extends AbstractPacket
{

    @Override
    public void deserialize(ChannelHandlerContext channel, PacketBuffer buf)
    {

    }

    @Override
    public ByteBuf serialize(ChannelHandlerContext channel)
    {

        final PacketBuffer buffer = new PacketBuffer();

        byte b = 0;
        b |= 1;
        b |= 2;
        b |= 4;
        buffer.writeVarInt(0x30);
        buffer.writeByte(b);
        buffer.writeFloat(1.5f);
        buffer.writeFloat(0.5f);

        return buffer;
    }
}
