package com.taahyt.pingu.messages.login;

import com.google.gson.Gson;
import com.taahyt.pingu.messages.AbstractMessage;
import com.taahyt.pingu.util.ChatComponent;
import com.taahyt.pingu.util.PacketBuffer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.SneakyThrows;
import org.json.JSONObject;

public class ClientboundDisconnectMessage extends AbstractMessage
{

    public ChatComponent chat;

    public ClientboundDisconnectMessage()
    {
        super(0x00);
    }

    @Override
    public void deserialize(ChannelHandlerContext channel, PacketBuffer buf)
    {

    }

    @SneakyThrows
    @Override
    public ByteBuf serialize(ChannelHandlerContext channel)
    {
        PacketBuffer buffer = new PacketBuffer();
        System.out.println("hi");
        buffer.writeVarInt(this.getPacketId());
        buffer.writeString(chat.toString());
        return buffer;
    }
}
