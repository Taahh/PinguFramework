package com.taahyt.pingu.messages.login;

import com.taahyt.pingu.PinguFramework;
import com.taahyt.pingu.messages.AbstractMessage;
import com.taahyt.pingu.player.Player;
import com.taahyt.pingu.util.MojangUtil;
import com.taahyt.pingu.util.packet.PacketBuffer;
import com.taahyt.pingu.util.Status;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.Getter;
import lombok.SneakyThrows;

import java.net.InetSocketAddress;
import java.util.UUID;

public class ClientboundLoginSuccessMessage extends AbstractMessage
{
    @Getter
    private final String username;

    public ClientboundLoginSuccessMessage(String username)
    {
        super(0x02);
        this.username = username;
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
            System.out.println("Login Success?");
            PacketBuffer buffer = new PacketBuffer();
            buffer.writeVarInt(this.getPacketId());

            UUID uuid = UUID.fromString(MojangUtil.getUUID(this.username));
            buffer.writeUUID(uuid);
            buffer.writeString(this.username);

            Player player = new Player(uuid, this.username, channel, (InetSocketAddress) channel.channel().remoteAddress());
            player.setSkinProperty(MojangUtil.getSkinProperty(player.getUuid()));
            PinguFramework.getServer().addPlayer(player);

            PinguFramework.CLIENTS.put(channel, Status.PLAY);
            return buffer;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
