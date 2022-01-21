package com.taahyt.pingu.util;

import com.google.common.collect.Lists;
import com.taahyt.pingu.util.packet.PacketBuffer;
import lombok.Data;

import java.util.List;

@Data
public class Tag
{

    private final String name;
    private final List<Integer> values = Lists.newArrayList();

    public void write(PacketBuffer buffer)
    {
        buffer.writeString(name);
        buffer.writeCollection(this.values, PacketBuffer::writeVarInt);
    }

}
