package dev.taah.pingu.chunk;

import dev.taah.pingu.handler.PacketBuffer;
import lombok.Data;

@Data
public class PalettedContainer
{
    private final byte bitsPerEntry;

    private Palette palette;
    private long[] data;


    public void write(PacketBuffer buffer)
    {
        buffer.writeByte(this.bitsPerEntry);
        this.palette.write(buffer);
        if (bitsPerEntry > 0)
        {
            buffer.writeLongArray(this.data);
        } else {
            buffer.writeVarInt(0);
        }
    }

}
