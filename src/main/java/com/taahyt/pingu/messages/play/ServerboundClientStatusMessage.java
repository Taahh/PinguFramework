package com.taahyt.pingu.messages.play;

import com.taahyt.pingu.PinguFramework;
import com.taahyt.pingu.messages.AbstractMessage;
import com.taahyt.pingu.util.PacketBuffer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public class ServerboundClientStatusMessage extends AbstractMessage
{
    public ServerboundClientStatusMessage()
    {
        super(0x04);
    }

    @Override
    public void deserialize(ChannelHandlerContext channel, PacketBuffer buf)
    {
        System.out.println("STATUS MESSAGE");
        PinguFramework.CLIENTS.remove(channel);
    }

    @Override
    public ByteBuf serialize(ChannelHandlerContext channel)
    {
        return null;
    }
}
