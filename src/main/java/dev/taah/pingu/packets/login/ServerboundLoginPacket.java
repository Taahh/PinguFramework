package dev.taah.pingu.packets.login;

import dev.taah.pingu.PinguFramework;
import dev.taah.pingu.entity.Player;
import dev.taah.pingu.handler.PacketBuffer;
import dev.taah.pingu.packets.AbstractPacket;
import dev.taah.pingu.packets.play.*;
import dev.taah.pingu.util.Logger;
import dev.taah.pingu.util.Profile;
import dev.taah.pingu.world.Location;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.util.Collections;
import java.util.EnumSet;
import java.util.UUID;

public class ServerboundLoginPacket extends AbstractPacket
{
    private UUID uuid;
    @Override
    public void deserialize(ChannelHandlerContext channel, PacketBuffer buf)
    {
        String name = buf.readString();
        boolean hasUuid = buf.readBoolean();
        Logger.log("Name: {0}; has uuid? {1}", name, hasUuid);
        if (hasUuid) {
            this.uuid = buf.readUUID();
            Logger.log("UUID: {0}", uuid);
            PinguFramework.SERVER.getPlayer(uuid).setProfile(new Profile(uuid, name));
            channel.channel().attr(Player.PLAYER_ATTRIBUTE_KEY).set(PinguFramework.SERVER.getPlayer(uuid));
            channel.writeAndFlush(new ClientboundLoginPacket(uuid).serialize(channel));
            channel.writeAndFlush(new ClientboundJoinPacket(PinguFramework.SERVER.getPlayer(uuid)).serialize(channel));

            channel.writeAndFlush(new ClientboundHoldItemPacket(0).serialize(channel));
            channel.writeAndFlush(new ClientboundRecipesPacket().serialize(channel));
            channel.writeAndFlush(new ClientboundTagsPacket().serialize(channel));
            channel.writeAndFlush(new ClientboundEntityEventPacket(channel.channel().attr(Player.PLAYER_ATTRIBUTE_KEY).get().getId(), 28).serialize(channel));
            channel.writeAndFlush(new ClientboundCommandsPacket().serialize(channel));
            channel.writeAndFlush(new ClientboundPlayerPosPacket(new Location(null, 0, 500, 0)).serialize(channel));

            final Player player = channel.channel().attr(Player.PLAYER_ATTRIBUTE_KEY).get();

            channel.writeAndFlush(new ClientboundPlayerInfoUpdatePacket(EnumSet.of(ClientboundPlayerInfoUpdatePacket.Action.ADD_PLAYER), Collections.singletonList(player)));
            channel.writeAndFlush(new ClientboundPlayerInfoUpdatePacket(EnumSet.of(ClientboundPlayerInfoUpdatePacket.Action.UPDATE_LATENCY), Collections.singletonList(player)));

            channel.writeAndFlush(new ClientboundDefaultSpawnPacket(new Location(null, 0, 500, 0)).serialize(channel));
            channel.writeAndFlush(new ClientboundChunkDataLightPacket().serialize(channel));
            channel.writeAndFlush(new ClientboundPlayerPosPacket(new Location(null, 0, 10, 0)).serialize(channel));
//            channel.writeAndFlush(new ClientboundRespawnPacket(player).serialize(channel));
        }


    }

    @Override
    public ByteBuf serialize(ChannelHandlerContext channel)
    {
        return null;
    }
}
