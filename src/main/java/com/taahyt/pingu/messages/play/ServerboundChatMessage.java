package com.taahyt.pingu.messages.play;

import com.taahyt.pingu.PinguFramework;
import com.taahyt.pingu.messages.AbstractMessage;
import com.taahyt.pingu.player.Player;
import com.taahyt.pingu.util.chat.ChatComponent;
import com.taahyt.pingu.util.packet.PacketBuffer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.net.InetSocketAddress;

public class ServerboundChatMessage extends AbstractMessage
{
    private String message;
    public ServerboundChatMessage()
    {
        super(0x03);
    }

    @Override
    public void deserialize(ChannelHandlerContext channel, PacketBuffer buf)
    {
        System.out.println("CHAT MESSAGE");
        this.message = buf.readString();
        Player player = PinguFramework.getServer().getPlayer((InetSocketAddress) channel.channel().remoteAddress());
        ChatComponent comp = new ChatComponent();
        comp.setText(this.message);
        if (player != null)
        {
            PinguFramework.getServer().getOnlinePlayers().forEach(p -> p.getConnection().writeAndFlush(new ClientboundChatMessage(comp, player.getUuid())));
        }
    }

    @Override
    public ByteBuf serialize(ChannelHandlerContext channel)
    {
        return null;
    }
}
