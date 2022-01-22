package com.taahyt.pingu.messages.play;

import com.google.common.collect.Lists;
import com.taahyt.pingu.PinguFramework;
import com.taahyt.pingu.messages.AbstractMessage;
import com.taahyt.pingu.player.Player;
import com.taahyt.pingu.server.World;
import com.taahyt.pingu.server.gamemode.GameMode;
import com.taahyt.pingu.util.packet.PacketBuffer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.querz.nbt.io.SNBTDeserializer;
import net.querz.nbt.tag.CompoundTag;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.List;

public class ClientboundJoinGameMessage extends AbstractMessage
{
    private final GameMode gameMode;
    private final Player player;

    public ClientboundJoinGameMessage(GameMode gameMode, Player player)
    {
        super(0x26);
        this.gameMode = gameMode;
        this.player = player;
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

        World world = new World("overworld");
        this.player.setCurrentWorld(world);
        this.player.setGameMode(this.gameMode);

        world.addEntity(this.player);


        buffer.writeVarInt(this.getPacketId());
        buffer.writeInt(this.player.getId());
        buffer.writeBoolean(false);
        buffer.writeByte(this.gameMode.getId());
        buffer.writeByte(-1);


        File file = new File("./test.snbt");
        try
        {
            CompoundTag compoundTag = (CompoundTag) new SNBTDeserializer().fromStream(new FileInputStream(file));
            CompoundTag dimensionTypes = compoundTag.getCompoundTag("minecraft:dimension_type");

            List<String> dimensions = Lists.newArrayList();
            dimensionTypes.getListTag("value").asCompoundTagList().forEach(tag ->
            {
                dimensions.add(tag.getString("name"));
            });

//            buffer.writeVarInt(dimensions.size());
            buffer.writeCollection(dimensions, PacketBuffer::writeString);
            buffer.writeNbt(compoundTag);
            dimensionTypes.getListTag("value").asCompoundTagList().forEach(tag ->
            {
                if (tag.getString("name").equalsIgnoreCase("minecraft:overworld"))
                {
                    CompoundTag dimensionType = tag.getCompoundTag("element");
                    buffer.writeNbt(dimensionType);
                    world.setDimensionType(dimensionType);
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
        PinguFramework.getServer().addWorld(world);

        return buffer;
    }
}
