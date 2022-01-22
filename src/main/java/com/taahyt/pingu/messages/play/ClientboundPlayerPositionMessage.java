package com.taahyt.pingu.messages.play;

import com.taahyt.pingu.PinguFramework;
import com.taahyt.pingu.messages.AbstractMessage;
import com.taahyt.pingu.player.Player;
import com.taahyt.pingu.server.math.Location;
import com.taahyt.pingu.util.packet.PacketBuffer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.Getter;

import java.net.InetSocketAddress;

@Getter
public class ClientboundPlayerPositionMessage extends AbstractMessage
{
    private final Location location;
    private final int teleportId;

    public ClientboundPlayerPositionMessage(Location location, int teleportId)
    {
        super(0x38);
        this.teleportId = teleportId;
        this.location = location;
    }

    @Override
    public void deserialize(ChannelHandlerContext channel, PacketBuffer buf)
    {

    }

    @Override
    public ByteBuf serialize(ChannelHandlerContext channel)
    {
        System.out.println("Position");
        PacketBuffer buffer = new PacketBuffer();

        buffer.writeVarInt(this.getPacketId());
        Player player = PinguFramework.getServer().getPlayer((InetSocketAddress) channel.channel().remoteAddress());
        if (player != null)
        {
            player.setLocation(this.location);
        }
        buffer.writeDouble(this.location == null ? 0 : this.location.getX());
        buffer.writeDouble(this.location == null ? 64 : this.location.getY());
        buffer.writeDouble(this.location == null ? 0 : this.location.getZ());
        buffer.writeFloat(this.location == null ? 180f : this.location.getYaw());
        buffer.writeFloat(this.location == null ? 0f : this.location.getPitch());
        buffer.writeByte(0);
        buffer.writeVarInt(this.teleportId);
        buffer.writeBoolean(false);
        return buffer;
    }
}
