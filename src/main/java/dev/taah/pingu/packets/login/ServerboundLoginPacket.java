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

import java.util.*;

public class ServerboundLoginPacket extends AbstractPacket
{
    private UUID uuid;

    @Override
    public void deserialize(ChannelHandlerContext channel, PacketBuffer buf)
    {
        String name = buf.readString();
        boolean hasUuid = buf.readBoolean();
        Logger.log("Name: {0}; has uuid? {1}", name, hasUuid);
        if (hasUuid)
        {
            this.uuid = buf.readUUID();
            Logger.log("UUID: {0}", uuid);
            PinguFramework.SERVER.getPlayer(uuid).setProfile(new Profile(uuid, name));
            channel.channel().attr(Player.PLAYER_ATTRIBUTE_KEY).set(PinguFramework.SERVER.getPlayer(uuid));

            final Player player = channel.channel().attr(Player.PLAYER_ATTRIBUTE_KEY).get();

            new Timer("keep-alive").schedule(new TimerTask()
            {
                @Override
                public void run()
                {
                    channel.writeAndFlush(new ClientboundKeepAlivePacket().serialize(channel));
                }
            }, 0, 5000);

            channel.writeAndFlush(new ClientboundLoginPacket(uuid).serialize(channel));
            channel.writeAndFlush(new ClientboundJoinPacket(PinguFramework.SERVER.getPlayer(uuid)).serialize(channel));
            channel.writeAndFlush(new ClientboundPluginMessagePacket().serialize(channel));


            channel.writeAndFlush(new ClientboundRespawnPacket(player).serialize(channel));

            channel.writeAndFlush(new ClientboundHoldItemPacket(0).serialize(channel));
            channel.writeAndFlush(new ClientboundRecipesPacket().serialize(channel));
            channel.writeAndFlush(new ClientboundTagsPacket().serialize(channel));
            channel.writeAndFlush(new ClientboundEntityEventPacket(channel.channel().attr(Player.PLAYER_ATTRIBUTE_KEY).get().getId(), 28).serialize(channel));
            channel.writeAndFlush(new ClientboundCommandsPacket().serialize(channel));
            channel.writeAndFlush(new ClientboundPlayerPosPacket(new Location(null, 0, 0, 0)).serialize(channel));

            channel.writeAndFlush(new ClientboundPlayerInfoUpdatePacket(EnumSet.of(ClientboundPlayerInfoUpdatePacket.Action.ADD_PLAYER), Collections.singletonList(player)));
            channel.writeAndFlush(new ClientboundPlayerInfoUpdatePacket(EnumSet.of(ClientboundPlayerInfoUpdatePacket.Action.UPDATE_LATENCY), Collections.singletonList(player)));

            for (int i = -10; i < 11; i++)
            {
                for (int j = -10; j < 11; j++)
                {
                    channel.writeAndFlush(new ClientboundChunkDataLightPacket(i, j).serialize(channel));
                }
            }

            channel.writeAndFlush(new ClientboundDefaultSpawnPacket(new Location(null, 0, 0, 0)).serialize(channel));
            channel.writeAndFlush(new ClientboundPlayerPosPacket(new Location(null, 0, 150, 0)).serialize(channel));
//            channel.writeAndFlush(new ClientboundPlayerAbilitiesPacket().serialize(channel));
        }
    }

    @Override
    public ByteBuf serialize(ChannelHandlerContext channel)
    {
        return null;
    }
}
