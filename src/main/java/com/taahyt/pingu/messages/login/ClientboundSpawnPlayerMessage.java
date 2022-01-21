package com.taahyt.pingu.messages.login;

import com.taahyt.pingu.messages.AbstractMessage;
import com.taahyt.pingu.util.packet.PacketBuffer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.SneakyThrows;

import java.util.UUID;

public class ClientboundSpawnPlayerMessage extends AbstractMessage
{
    public UUID uuid;

    public ClientboundSpawnPlayerMessage()
    {
        super(0x04);
    }

    @Override
    public void deserialize(ChannelHandlerContext channel, PacketBuffer buf)
    {

    }

    @SneakyThrows
    @Override
    public ByteBuf serialize(ChannelHandlerContext channel)
    {
        try {
            System.out.println("Spawn Player");
            PacketBuffer buffer = new PacketBuffer();
            buffer.writeVarInt(this.getPacketId());

            buffer.writeVarInt(0);
            buffer.writeUUID(uuid);
            buffer.writeDouble(1);
            buffer.writeDouble(5);
            buffer.writeDouble(1);
            buffer.writeByte(0);
            buffer.writeByte(0);
            return buffer;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
