package dev.taah.pingu.chunk;

import dev.taah.pingu.handler.PacketBuffer;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SingleValuePalette implements Palette
{

    private final int blockId;

    @Override
    public byte getBitsPerEntry()
    {
        return 0;
    }

    @Override
    public void write(PacketBuffer buffer)
    {
        buffer.writeVarInt(this.blockId);
    }

    @Override
    public void deserialize(PacketBuffer buffer)
    {

    }
}
