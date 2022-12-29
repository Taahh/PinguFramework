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
            channel.writeAndFlush(new ClientboundHoldItemPacket(0).serialize(channel));
            channel.writeAndFlush(new ClientboundRecipesPacket().serialize(channel));
            channel.writeAndFlush(new ClientboundTagsPacket().serialize(channel));
            channel.writeAndFlush(new ClientboundEntityEventPacket(channel.channel().attr(Player.PLAYER_ATTRIBUTE_KEY).get().getId(), 28).serialize(channel));
            channel.writeAndFlush(new ClientboundCommandsPacket().serialize(channel));
            channel.writeAndFlush(new ClientboundPlayerPosPacket(new Location(null, 0, 0, 0)).serialize(channel));

            final Player player = channel.channel().attr(Player.PLAYER_ATTRIBUTE_KEY).get();

            channel.writeAndFlush(new ClientboundPlayerInfoUpdatePacket(EnumSet.of(ClientboundPlayerInfoUpdatePacket.Action.ADD_PLAYER), Collections.singletonList(player)));
            channel.writeAndFlush(new ClientboundPlayerInfoUpdatePacket(EnumSet.of(ClientboundPlayerInfoUpdatePacket.Action.UPDATE_LATENCY), Collections.singletonList(player)));
            channel.writeAndFlush(new ClientboundPlayerPosPacket(new Location(null, 0, 0, 0)).serialize(channel));
            channel.writeAndFlush(new ClientboundRespawnPacket(player).serialize(channel));
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
