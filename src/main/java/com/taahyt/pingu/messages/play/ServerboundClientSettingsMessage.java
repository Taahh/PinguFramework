package com.taahyt.pingu.messages.play;

import com.taahyt.pingu.messages.AbstractMessage;
import com.taahyt.pingu.util.packet.PacketBuffer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.querz.nbt.tag.CompoundTag;

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

        ClientboundRecipePacket recipe = new ClientboundRecipePacket();
        channel.writeAndFlush(recipe.serialize(channel));

        ClientboundTagPacket tags = new ClientboundTagPacket();
        channel.writeAndFlush(tags.serialize(channel));
        CompoundTag tag = new CompoundTag();

        /*ClientboundEntityStatusPacket entityStatusPacket = new ClientboundEntityStatusPacket();
        channel.writeAndFlush(entityStatusPacket.serialize(channel));

        ClientboundCommandPacket clientboundCommandPacket = new ClientboundCommandPacket();
        channel.writeAndFlush(clientboundCommandPacket.serialize(channel));*/
    }

    @Override
    public ByteBuf serialize(ChannelHandlerContext channel)
    {
        return null;
    }
}
