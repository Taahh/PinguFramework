package dev.taah.pingu.packets.play;

import com.google.common.hash.Hashing;
import dev.taah.pingu.PinguFramework;
import dev.taah.pingu.entity.Player;
import dev.taah.pingu.handler.PacketBuffer;
import dev.taah.pingu.packets.AbstractPacket;
import dev.taah.pingu.server.GameMode;
import dev.taah.pingu.server.Server;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.kyori.adventure.nbt.CompoundBinaryTag;
import net.kyori.adventure.nbt.ListBinaryTag;

import java.util.stream.Collectors;

public class ClientboundRespawnPacket extends AbstractPacket
{
    private final Player player;

    public ClientboundRespawnPacket(Player player)
    {
        this.player = player;
    }

    @Override
    public void deserialize(ChannelHandlerContext channel, PacketBuffer buf)
    {

    }

    @Override
    public ByteBuf serialize(ChannelHandlerContext channel)
    {
        final Server server = PinguFramework.SERVER;
        final PacketBuffer buffer = new PacketBuffer();

        buffer.writeVarInt(0x3D);
        buffer.writeString("minecraft:overworld");
        buffer.writeString("minecraft:overworld");
        buffer.writeLong(Hashing.sha256().hashLong("North Carolina".hashCode()).asLong());
        buffer.writeByte(player.getGameMode() == GameMode.UNDEFINED ? server.getServerSettings().gameMode.getData() : player.getGameMode().getData());
        buffer.writeByte(-1);
        buffer.writeBoolean(false);
        buffer.writeBoolean(false);
        buffer.writeBoolean(false);
        buffer.writeBoolean(false);

        return buffer;
    }
}
