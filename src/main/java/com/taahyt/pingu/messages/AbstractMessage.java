package com.taahyt.pingu.messages;

import com.taahyt.pingu.util.PacketBuffer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.Data;

@Data
public abstract class AbstractMessage
{
    private final int packetId;

    public abstract void deserialize(ChannelHandlerContext channel, PacketBuffer buf);
    public abstract ByteBuf serialize(ChannelHandlerContext channel);
}
