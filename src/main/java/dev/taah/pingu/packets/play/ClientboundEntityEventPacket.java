package dev.taah.pingu.packets.play;

import dev.taah.pingu.PinguFramework;
import dev.taah.pingu.handler.PacketBuffer;
import dev.taah.pingu.packets.AbstractPacket;
import dev.taah.pingu.server.internals.Identifier;
import dev.taah.pingu.server.internals.Tag;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class ClientboundEntityEventPacket extends AbstractPacket
{

    private final int entityId;
    private final int status;

    @Override
    public void deserialize(ChannelHandlerContext channel, PacketBuffer buf)
    {

    }

    @Override
    public ByteBuf serialize(ChannelHandlerContext channel)
    {

        final PacketBuffer buffer = new PacketBuffer();

        buffer.writeVarInt(0x19);
        buffer.writeInt(this.entityId);
        buffer.writeByte(this.status);

        return buffer;
    }
}
