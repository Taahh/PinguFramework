package dev.taah.pingu.chunk;

import dev.taah.pingu.handler.PacketBuffer;
import lombok.Data;

@Data
public class ChunkSection
{
    private int blockCount;
    private PalettedContainer blockStates;
    private PalettedContainer biomes;

    public void write(PacketBuffer buffer)
    {
        buffer.writeShort(this.blockCount);
        blockStates.write(buffer);
        biomes.write(buffer);
    }
}
