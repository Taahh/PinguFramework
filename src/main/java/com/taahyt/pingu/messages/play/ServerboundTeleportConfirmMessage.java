package com.taahyt.pingu.messages.play;

import com.taahyt.pingu.messages.AbstractMessage;
import com.taahyt.pingu.util.packet.PacketBuffer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public class ServerboundTeleportConfirmMessage extends AbstractMessage
{

    public int teleportId;

    public ServerboundTeleportConfirmMessage()
    {
        super(0x00);
    }


    @Override
    public void deserialize(ChannelHandlerContext channel, PacketBuffer buf)
    {
        System.out.println("DESERIALIZING TELEPORT CONFIRM");
        this.teleportId = buf.readVarInt();
        ClientboundPlayerPositionMessage clientboundPlayerPositionMessage = new ClientboundPlayerPositionMessage(this.teleportId);
        channel.writeAndFlush(clientboundPlayerPositionMessage.serialize(channel));
    }

    @Override
    public ByteBuf serialize(ChannelHandlerContext channel)
    {
        return null;
    }
}
