package dev.taah.pingu.packets.play;

import dev.taah.pingu.client.ChatMode;
import dev.taah.pingu.client.MainHand;
import dev.taah.pingu.entity.Player;
import dev.taah.pingu.handler.PacketBuffer;
import dev.taah.pingu.packets.AbstractPacket;
import dev.taah.pingu.util.Logger;
import dev.taah.pingu.world.Location;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.util.Collections;
import java.util.EnumSet;

public class ServerboundConfirmTeleportPacket extends AbstractPacket
{
    @Override
    public void deserialize(ChannelHandlerContext channel, PacketBuffer buf)
    {
       int teleportId = buf.readVarInt();
       System.out.println("RECEIVED TELEPORT CONFIRMATION");
    }

    @Override
    public ByteBuf serialize(ChannelHandlerContext channel)
    {
        return null;
    }
}
