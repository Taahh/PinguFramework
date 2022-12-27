package dev.taah.pingu.packets.login;

import dev.taah.pingu.PinguFramework;
import dev.taah.pingu.client.Status;
import dev.taah.pingu.entity.Player;
import dev.taah.pingu.handler.PacketBuffer;
import dev.taah.pingu.packets.AbstractPacket;
import dev.taah.pingu.util.Logger;
import dev.taah.pingu.util.Profile;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.UUID;

@RequiredArgsConstructor
public class ClientboundLoginPacket extends AbstractPacket
{
    private final UUID uuid;

    @Override
    public void deserialize(ChannelHandlerContext channel, PacketBuffer buf)
    {
    }

    @Override
    public ByteBuf serialize(ChannelHandlerContext channel)
    {
        final Player player = PinguFramework.SERVER.getPlayer(uuid);
        PacketBuffer buffer = new PacketBuffer();
        buffer.writeVarInt(0x02);
        buffer.writeUUID(uuid);
        buffer.writeString(player.getProfile().getUsername());
        buffer.writeCollection(player.getProfile().getProperties(), (packetBuffer, property) -> {
            packetBuffer.writeString(property.getName());
            packetBuffer.writeString(property.getValue());
            packetBuffer.writeBoolean(property.isSigned());
            packetBuffer.writeString(property.getSignature());
        });

        PinguFramework.CLIENTS.put(channel, Status.PLAY);
//        channel.writeAndFlush(new dev.taah.pingu.packets.play.ClientboundLoginPacket(player).serialize(channel));

        return buffer;
    }
}
