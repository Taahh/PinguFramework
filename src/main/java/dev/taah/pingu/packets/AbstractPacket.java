package dev.taah.pingu.packets;

import dev.taah.pingu.handler.PacketBuffer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.Data;

@Data
public abstract class AbstractPacket
{
    public abstract void deserialize(ChannelHandlerContext channel, PacketBuffer buf);

    public abstract ByteBuf serialize(ChannelHandlerContext channel);
}
