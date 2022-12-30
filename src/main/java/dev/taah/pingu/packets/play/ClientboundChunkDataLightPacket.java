package dev.taah.pingu.packets.play;

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

    @Override
    public void deserialize(ChannelHandlerContext channel, PacketBuffer buf)
    {

    }

    @Override
    public ByteBuf serialize(ChannelHandlerContext channel)
    {

        final PacketBuffer buffer = new PacketBuffer();

        buffer.writeVarInt(0x20);
        buffer.writeInt(0); // x
        buffer.writeInt(0); // y

        final CompoundBinaryTag heightmaps = CompoundBinaryTag.empty();
        heightmaps.put("MOTION_BLOCKING", ListBinaryTag.empty());

        buffer.writeNbt(heightmaps);

        final PacketBuffer chunkSection = new PacketBuffer();
        chunkSection.writeShort(4096);
//        for (int i = 0; i < 4096; i++)
//        {
        chunkSection.writeByte(0);
        chunkSection.writeVarInt(1);
        chunkSection.writeVarInt(0);
//        }
//        for (int i = 0; i < 64; i++)
//        {
        chunkSection.writeByte(0);
        chunkSection.writeVarInt(55);
        chunkSection.writeVarInt(0);
//        }

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

    public enum RelativeArgument
    {
        X(0),
        Y(1),
        Z(2),
        Y_ROT(3),
        X_ROT(4);

        public static final Set<ClientboundChunkDataLightPacket.RelativeArgument> ALL = Set.of(values());
        public static final Set<ClientboundChunkDataLightPacket.RelativeArgument> ROTATION = Set.of(X_ROT, Y_ROT);
        private final int bit;

        private RelativeArgument(int shift)
        {
            this.bit = shift;
        }

        private int getMask()
        {
            return 1 << this.bit;
        }

        private boolean isSet(int mask)
        {
            return (mask & this.getMask()) == this.getMask();
        }

        public static Set<ClientboundChunkDataLightPacket.RelativeArgument> unpack(int mask)
        {
            Set<ClientboundChunkDataLightPacket.RelativeArgument> set = EnumSet.noneOf(ClientboundChunkDataLightPacket.RelativeArgument.class);

            for (ClientboundChunkDataLightPacket.RelativeArgument relativeArgument : values())
            {
                if (relativeArgument.isSet(mask))
                {
                    set.add(relativeArgument);
                }
            }

            return set;
        }

        public static int pack(Set<ClientboundChunkDataLightPacket.RelativeArgument> flags)
        {
            int i = 0;

            for (ClientboundChunkDataLightPacket.RelativeArgument relativeArgument : flags)
            {
                i |= relativeArgument.getMask();
            }

            return i;
        }
    }
}
