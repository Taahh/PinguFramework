package com.taahyt.pingu.messages.play;

import com.taahyt.pingu.messages.AbstractMessage;
import com.taahyt.pingu.player.Player;
import com.taahyt.pingu.util.packet.PacketBuffer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public class ClientboundPlayerInfoMessage extends AbstractMessage
{
    private final Action action;
    private final Player player;
    public ClientboundPlayerInfoMessage(Action action, Player player)
    {
        super(0x36);
        this.action = action;
        this.player = player;
    }

    @Override
    public void deserialize(ChannelHandlerContext channel, PacketBuffer buf)
    {

    }

    @Override
    public ByteBuf serialize(ChannelHandlerContext channel)
    {
        System.out.println("Player Info");
        PacketBuffer buffer = new PacketBuffer();
        buffer.writeVarInt(this.getPacketId());
        buffer.writeVarInt(this.action.getId());
        buffer.writeVarInt(1);
        buffer.writeUUID(this.player.getUuid());

        switch (action)
        {
            case ADD:
                buffer.writeString(this.player.getName());
                buffer.writeVarInt(1);
                buffer.writeString("textures");
                buffer.writeString(this.player.getSkinProperty().getValue());
                buffer.writeBoolean(true);
                buffer.writeString(this.player.getSkinProperty().getSignature());
                buffer.writeVarInt(this.player.getGameMode().getId());
                buffer.writeVarInt(150);
                buffer.writeBoolean(false);
                break;
            case UPDATE_LATENCY:
                buffer.writeVarInt(150);
                break;
            case REMOVE:
                break;
        }
        return buffer;
    }

    public enum Action {

        ADD(0), UPDATE_GAMEMODE(1), UPDATE_LATENCY(2), UPDATE_DISPLAY_NAME(3), REMOVE(4);

        private final int id;
        Action(int id)
        {
            this.id = id;
        }

        public int getId()
        {
            return this.id;
        }
    }
}
