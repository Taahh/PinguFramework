package com.taahyt.pingu.messages.status;

import com.taahyt.pingu.messages.AbstractMessage;
import com.taahyt.pingu.util.chat.ChatColor;
import com.taahyt.pingu.util.chat.ChatComponent;
import com.taahyt.pingu.util.packet.PacketBuffer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.util.UUID;

public class ClientboundRequestMessage extends AbstractMessage
{

    public ClientboundRequestMessage()
    {
        super(0x00);
    }

    @Override
    public void deserialize(ChannelHandlerContext channel, PacketBuffer buf)
    {

    }

    @Override
    public ByteBuf serialize(ChannelHandlerContext channel)
    {
        System.out.println("Serializing Response");

        ChatComponent component = new ChatComponent();
        component.color = ChatColor.RED.getName();
        component.text = "Pingu Framework";

        PacketBuffer buffer = new PacketBuffer();
        buffer.writeVarInt(0x00);
        buffer.writeString(String.format("""
                {
                    "version": {
                        "name": "Pingu",
                        "protocol": 757
                    },
                    "players": {
                        "max": 1,
                        "online": 0,
                        "sample": []
                    },
                    "description": %s
                }""", component, UUID.randomUUID()));
        return buffer;
    }
}
