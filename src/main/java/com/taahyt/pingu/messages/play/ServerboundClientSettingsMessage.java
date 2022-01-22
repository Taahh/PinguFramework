package com.taahyt.pingu.messages.play;

import com.taahyt.pingu.PinguFramework;
import com.taahyt.pingu.equipment.MainHand;
import com.taahyt.pingu.messages.AbstractMessage;
import com.taahyt.pingu.player.Player;
import com.taahyt.pingu.server.math.Location;
import com.taahyt.pingu.util.chat.ChatMode;
import com.taahyt.pingu.util.packet.PacketBuffer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.net.InetSocketAddress;

public class ServerboundClientSettingsMessage extends AbstractMessage
{

    public String locale;
    public byte viewDistance;
    public ChatMode chatMode;
    public boolean chatColors;
    public short skinParts;
    public MainHand hand;
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
        this.chatMode = ChatMode.getById(buf.readVarInt());
        this.chatColors = buf.readBoolean();
        this.skinParts = buf.readUnsignedByte();
        this.hand = MainHand.getById(buf.readVarInt());
        this.textFiltering = buf.readBoolean();
        this.serverListings = buf.readBoolean();

        Player player = PinguFramework.getServer().getPlayer((InetSocketAddress) channel.channel().remoteAddress());
        if (player != null)
        {
            player.setLocale(this.locale);
            player.setViewDistance(this.viewDistance);
            player.setChatMode(this.chatMode);
            player.setSkinParts(this.skinParts);
            player.setHand(this.hand);
            player.setTextFiltering(this.textFiltering);
            player.setServerListings(this.serverListings);

            ClientboundSlotMessage message = new ClientboundSlotMessage(0);
            channel.writeAndFlush(message.serialize(channel));

            ClientboundRecipeMessage recipe = new ClientboundRecipeMessage();
            channel.writeAndFlush(recipe.serialize(channel));

            ClientboundTagMessage tags = new ClientboundTagMessage();
            channel.writeAndFlush(tags.serialize(channel));

            ClientboundEntityStatusMessage entityStatusPacket = new ClientboundEntityStatusMessage(player.getId());
            channel.writeAndFlush(entityStatusPacket.serialize(channel));

            ClientboundCommandMessage clientboundCommandMessage = new ClientboundCommandMessage();
            channel.writeAndFlush(clientboundCommandMessage.serialize(channel));

            ClientboundPlayerPositionMessage clientboundPlayerPositionMessage = new ClientboundPlayerPositionMessage(new Location(player.getCurrentWorld(), 0, 64, 0), 0);
            channel.writeAndFlush(clientboundPlayerPositionMessage.serialize(channel));

            ClientboundPlayerInfoMessage addPlayer = new ClientboundPlayerInfoMessage(ClientboundPlayerInfoMessage.Action.ADD, player);
            channel.writeAndFlush(addPlayer.serialize(channel));

            ClientboundPlayerInfoMessage updatePing = new ClientboundPlayerInfoMessage(ClientboundPlayerInfoMessage.Action.UPDATE_LATENCY, player);
            channel.writeAndFlush(updatePing.serialize(channel));

            channel.writeAndFlush(new ClientboundPlayerPositionMessage(player.getLocation(), 0).serialize(channel));
        }
    }

    @Override
    public ByteBuf serialize(ChannelHandlerContext channel)
    {
        return null;
    }
}
