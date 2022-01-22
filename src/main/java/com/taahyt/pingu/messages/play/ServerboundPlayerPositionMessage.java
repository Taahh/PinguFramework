package com.taahyt.pingu.messages.play;

import com.taahyt.pingu.PinguFramework;
import com.taahyt.pingu.messages.AbstractMessage;
import com.taahyt.pingu.player.Player;
import com.taahyt.pingu.util.packet.PacketBuffer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.net.InetSocketAddress;

public class ServerboundPlayerPositionMessage extends AbstractMessage
{
    private double x;
    private double y;
    private double z;

    private boolean onGround;

    public ServerboundPlayerPositionMessage()
    {
        super(0x11);
    }


    @Override
    public void deserialize(ChannelHandlerContext channel, PacketBuffer buf)
    {
        System.out.println("DESERIALIZING PLAYER POS");
        this.x = buf.readDouble();
        this.y = buf.readDouble();
        this.z = buf.readDouble();
        this.onGround = buf.readBoolean();
        Player player = PinguFramework.getServer().getPlayer((InetSocketAddress) channel.channel().remoteAddress());
        if (player != null)
        {
            player.getLocation().setX(this.x);
            player.getLocation().setY(this.y);
            player.getLocation().setZ(this.z);
            player.setOnGround(this.onGround);
        }
    }

    @Override
    public ByteBuf serialize(ChannelHandlerContext channel)
    {
        return null;
    }

}
