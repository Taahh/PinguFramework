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
import lombok.extern.java.Log;

import java.util.Collections;
import java.util.EnumSet;

public class ServerboundClientInfoPacket extends AbstractPacket
{
    @Override
    public void deserialize(ChannelHandlerContext channel, PacketBuffer buf)
    {
        if (buf.readableBytes() < 1)
        {
            return;
        }
        try
        {
            String locale = buf.readString(16);
            byte viewDistance = buf.readByte();
            ChatMode chatMode = ChatMode.getByData(buf.readVarInt());
            boolean chatColors = buf.readBoolean();
            byte skinParts = buf.readByte();
            MainHand mainHand = MainHand.getByData(buf.readVarInt());
            boolean textFiltering = buf.readBoolean();
            boolean serverListings = buf.readBoolean();
            Logger.log("CLIENT INFO {0}, {1}, {2}, {3}, {4}, {5}, {6}, {7}", locale, viewDistance, chatMode.name(), chatColors, skinParts, mainHand.name(), textFiltering, serverListings);
        }
        catch (Exception e)
        {

        }
    }

    @Override
    public ByteBuf serialize(ChannelHandlerContext channel)
    {
        return null;
    }
}
