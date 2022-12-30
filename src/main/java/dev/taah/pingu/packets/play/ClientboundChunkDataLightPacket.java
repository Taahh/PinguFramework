package dev.taah.pingu.packets.play;

import dev.taah.pingu.chunk.ChunkSection;
import dev.taah.pingu.chunk.PalettedContainer;
import dev.taah.pingu.chunk.SingleValuePalette;
import dev.taah.pingu.handler.PacketBuffer;
import dev.taah.pingu.packets.AbstractPacket;
import dev.taah.pingu.world.Location;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.nbt.CompoundBinaryTag;
import net.kyori.adventure.nbt.ListBinaryTag;

import java.util.EnumSet;
import java.util.Set;

@RequiredArgsConstructor
public class ClientboundChunkDataLightPacket extends AbstractPacket
{
    private final int x;
    private final int z;

    @Override
    public void deserialize(ChannelHandlerContext channel, PacketBuffer buf)
    {

    }

    @Override
    public ByteBuf serialize(ChannelHandlerContext channel)
    {

        final PacketBuffer buffer = new PacketBuffer();

        buffer.writeVarInt(0x20);
        buffer.writeInt(x); // x
        buffer.writeInt(z); // y

        final CompoundBinaryTag heightmaps = CompoundBinaryTag.empty();
//        heightmaps.put("MOTION_BLOCKING", ListBinaryTag.empty());

        buffer.writeNbt(heightmaps);


        final PacketBuffer chunkSection = new PacketBuffer();

        for (int i = 0; i < 24; i++)
        {
            final ChunkSection section = new ChunkSection();
            section.setBlockCount(24);

            PalettedContainer states = new PalettedContainer((byte) 0);
            states.setPalette(new SingleValuePalette(1));

            PalettedContainer biomes = new PalettedContainer((byte) 0);
            biomes.setPalette(new SingleValuePalette(1));

            section.setBlockStates(states);
            section.setBiomes(biomes);

            section.write(chunkSection);
        }

        buffer.writeByteArray(chunkSection.getByteArray());

        buffer.writeVarInt(0);
        buffer.writeBoolean(true);
        buffer.writeByte(0);
        buffer.writeByte(0);
        buffer.writeByte(0);
        buffer.writeByte(0);
        buffer.writeVarInt(0);
        buffer.writeVarInt(0);

        return buffer;
    }
}
