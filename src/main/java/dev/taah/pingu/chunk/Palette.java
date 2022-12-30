package dev.taah.pingu.chunk;

import dev.taah.pingu.handler.PacketBuffer;

public interface Palette
{
    byte getBitsPerEntry();

    void write(PacketBuffer buffer);

    void deserialize(PacketBuffer buffer);
}
