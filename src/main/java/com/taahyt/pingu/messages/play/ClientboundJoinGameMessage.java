package com.taahyt.pingu.messages.play;

import com.google.common.collect.Lists;
import com.taahyt.pingu.messages.AbstractMessage;
import com.taahyt.pingu.util.packet.PacketBuffer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.querz.nbt.io.SNBTDeserializer;
import net.querz.nbt.tag.CompoundTag;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

public class ClientboundJoinGameMessage extends AbstractMessage
{

    public ClientboundJoinGameMessage()
    {
        super(0x26);
    }

    @Override
    public void deserialize(ChannelHandlerContext channel, PacketBuffer buf)
    {

    }

    @Override
    public ByteBuf serialize(ChannelHandlerContext channel)
    {
        System.out.println("CLIENTBOUND JOIN GAME");
        PacketBuffer buffer = new PacketBuffer();
        buffer.writeVarInt(this.getPacketId());
        buffer.writeInt(0);
        buffer.writeBoolean(false);
        buffer.writeByte(1);
        buffer.writeByte(-1);

        File file = new File("./test.snbt");
        try
        {
            CompoundTag compoundTag = (CompoundTag) new SNBTDeserializer().fromStream(new FileInputStream(file));
            CompoundTag dimensionTypes = compoundTag.getCompoundTag("minecraft:dimension_type");

            List<String> dimensions = Lists.newArrayList();
            dimensionTypes.getListTag("value").asCompoundTagList().forEach(tag -> {
                dimensions.add(tag.getString("name"));
            });

//            buffer.writeVarInt(dimensions.size());
            buffer.writeCollection(dimensions, PacketBuffer::writeString);
            buffer.writeNbt(compoundTag);
            dimensionTypes.getListTag("value").asCompoundTagList().forEach(tag -> {
                if (tag.getString("name").equalsIgnoreCase("minecraft:overworld"))
                {
                    buffer.writeNbt(tag.getCompoundTag("element"));
                }
            });

        } catch (IOException e)
        {
            e.printStackTrace();
        }

        buffer.writeString("minecraft:overworld");
        buffer.writeLong("North Carolina".hashCode());
        buffer.writeVarInt(5);
        buffer.writeVarInt(16);
        buffer.writeVarInt(20);
        buffer.writeBoolean(false);
        buffer.writeBoolean(false);
        buffer.writeBoolean(true);
        buffer.writeBoolean(true);

        return buffer;
    }
}
