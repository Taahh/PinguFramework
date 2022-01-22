package com.taahyt.pingu.messages.play;

import com.google.gson.Gson;
import com.taahyt.pingu.messages.AbstractMessage;
import com.taahyt.pingu.util.chat.ChatComponent;
import com.taahyt.pingu.util.packet.PacketBuffer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.util.UUID;

public class ClientboundChatMessage extends AbstractMessage
{
    private final ChatComponent message;
    private final UUID sender;
    public ClientboundChatMessage(ChatComponent message, UUID sender)
    {
        super(0x0F);
        this.message = message;
        this.sender = sender;
    }

    @Override
    public void deserialize(ChannelHandlerContext channel, PacketBuffer buf)
    {
    }

    @Override
    public ByteBuf serialize(ChannelHandlerContext channel)
    {
        PacketBuffer buffer = new PacketBuffer();
        buffer.writeString(message.toString());
        buffer.writeByte(0);
        buffer.writeUUID(sender);
        return null;
    }
}
