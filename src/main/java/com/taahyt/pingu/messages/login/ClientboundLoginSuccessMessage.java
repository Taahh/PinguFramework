package com.taahyt.pingu.messages.login;

import com.taahyt.pingu.PinguFramework;
import com.taahyt.pingu.messages.AbstractMessage;
import com.taahyt.pingu.util.packet.PacketBuffer;
import com.taahyt.pingu.util.Status;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.SneakyThrows;

import java.util.UUID;

public class ClientboundLoginSuccessMessage extends AbstractMessage
{
    public String username;

    public ClientboundLoginSuccessMessage()
    {
        super(0x02);
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
            UUID uuid;
            System.out.println("Login Success?");
            PacketBuffer buffer = new PacketBuffer();
            buffer.writeVarInt(this.getPacketId());
            buffer.writeUUID(uuid = UUID.randomUUID());
            buffer.writeString(this.username);
            PinguFramework.CLIENTS.put(channel, Status.PLAY);

            /*ClientboundSpawnPlayerMessage message = new ClientboundSpawnPlayerMessage();
            message.uuid = uuid;*/

            /*ClientboundDisconnectMessage message = new ClientboundDisconnectMessage();
            ChatComponent component = new ChatComponent();
            component.text = "Hey There\nHi\nHi\nHi\nHi\nHi\nHi\nHi\nHi\nHi\nHi\nHi\nHi\nHi\nHi\nHi\nHi\nHi\nHi\nHi\nHi";
            component.bold = true;
            component.color = ChatColor.RED.getName();
            message.chat = component;
            channel.writeAndFlush(message.serialize(channel)).sync();
            System.out.println("???");*/
            return buffer;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
