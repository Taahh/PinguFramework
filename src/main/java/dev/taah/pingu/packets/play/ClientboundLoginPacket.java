package dev.taah.pingu.packets.play;

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

public class ClientboundLoginPacket extends AbstractPacket
{
    private final Player player;
    public ClientboundLoginPacket(Player player)
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
        final CompoundBinaryTag registryCodec = server.getLoader().getRegistryCodec();

        final PacketBuffer buffer = new PacketBuffer();

        buffer.writeVarInt(0x25);
        buffer.writeInt(player.getId());
        buffer.writeBoolean(server.getServerSettings().hardcore);
        buffer.writeByte(server.getServerSettings().gameMode.getNumber());
        buffer.writeByte(player.getGameMode() == GameMode.UNDEFINED ? server.getServerSettings().gameMode.getNumber() : player.getGameMode().getNumber());

        final ListBinaryTag dimensions = (ListBinaryTag) registryCodec.getCompound("minecraft:dimension_type").get("value");
        buffer.writeCollection(dimensions.stream().map(binaryTag -> ((CompoundBinaryTag)binaryTag).getString("name")).collect(Collectors.toList()), (packetBuffer, s) -> {
            System.out.println(s);
            packetBuffer.writeString(s);
        });
        buffer.writeNbt(registryCodec);
        buffer.writeString("minecraft:dimension_type");
        buffer.writeString("minecraft:overworld");
        buffer.writeLong("North Carolina".hashCode());
        buffer.writeVarInt(100);
        buffer.writeVarInt(16);
        buffer.writeVarInt(16);
        buffer.writeBoolean(false);
        buffer.writeBoolean(true);
        buffer.writeBoolean(true);
        buffer.writeBoolean(false);
        buffer.writeBoolean(false);

        return buffer;
    }
}
