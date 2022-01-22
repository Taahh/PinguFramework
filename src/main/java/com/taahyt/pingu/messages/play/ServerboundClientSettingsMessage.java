package com.taahyt.pingu.messages.play;

import com.taahyt.pingu.messages.AbstractMessage;
import com.taahyt.pingu.util.packet.PacketBuffer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public class ServerboundClientSettingsMessage extends AbstractMessage
{

    public String locale;
    public byte viewDistance;
    public int chatMode;
    public boolean chatColors;
    public short skinParts;
    public int hand;
    public boolean textFiltering;
    public boolean serverListings;

    public ServerboundClientSettingsMessage()
    {
        super(0x05);
    }


    @Override
    public void deserialize(ChannelHandlerContext channel, PacketBuffer buf)
    {
        System.out.println("DESERIALIZING CLIENT SETTINGS");
        this.locale = buf.readString();
        this.viewDistance = buf.readByte();
        this.chatMode = buf.readVarInt();
        this.chatColors = buf.readBoolean();
        this.skinParts = buf.readUnsignedByte();
        this.hand = buf.readVarInt();
        this.textFiltering = buf.readBoolean();
        this.serverListings = buf.readBoolean();

        ClientboundSlotMessage message = new ClientboundSlotMessage();
        channel.writeAndFlush(message.serialize(channel));

        ClientboundRecipeMessage recipe = new ClientboundRecipeMessage();
        channel.writeAndFlush(recipe.serialize(channel));

        ClientboundTagMessage tags = new ClientboundTagMessage();
        channel.writeAndFlush(tags.serialize(channel));

        ClientboundEntityStatusMessage entityStatusPacket = new ClientboundEntityStatusMessage();
        channel.writeAndFlush(entityStatusPacket.serialize(channel));

        ClientboundCommandMessage clientboundCommandMessage = new ClientboundCommandMessage();
        channel.writeAndFlush(clientboundCommandMessage.serialize(channel));

        ClientboundPlayerPositionMessage clientboundPlayerPositionMessage = new ClientboundPlayerPositionMessage(0);
        channel.writeAndFlush(clientboundPlayerPositionMessage.serialize(channel));

        ClientboundPlayerInfoMessage playerInfoMessage = new ClientboundPlayerInfoMessage();
        channel.writeAndFlush(playerInfoMessage.serialize(channel));
    }

    @Override
    public ByteBuf serialize(ChannelHandlerContext channel)
    {
        return null;
    }
}
