package dev.taah.pingu.packets.play;

import dev.taah.pingu.handler.PacketBuffer;
import dev.taah.pingu.packets.AbstractPacket;
import dev.taah.pingu.world.Location;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.RequiredArgsConstructor;

import java.util.EnumSet;
import java.util.Set;

@RequiredArgsConstructor
public class ClientboundDefaultSpawnPacket extends AbstractPacket
{
    private final Location location;

    @Override
    public void deserialize(ChannelHandlerContext channel, PacketBuffer buf)
    {

    }

    @Override
    public ByteBuf serialize(ChannelHandlerContext channel)
    {

        final PacketBuffer buffer = new PacketBuffer();

        buffer.writeVarInt(0x4C);
        buffer.writeLocation(location);
//        buffer.writeLong((((int)location.getX() & 0x3FFFFFF) << 38) | (((int)location.getY() & 0x3FFFFFF) << 12) | ((int)location.getZ() & 0xFFF));
        buffer.writeFloat(90);

        return buffer;
    }

    public enum RelativeArgument {
        X(0),
        Y(1),
        Z(2),
        Y_ROT(3),
        X_ROT(4);

        public static final Set<ClientboundDefaultSpawnPacket.RelativeArgument> ALL = Set.of(values());
        public static final Set<ClientboundDefaultSpawnPacket.RelativeArgument> ROTATION = Set.of(X_ROT, Y_ROT);
        private final int bit;

        private RelativeArgument(int shift) {
            this.bit = shift;
        }

        private int getMask() {
            return 1 << this.bit;
        }

        private boolean isSet(int mask) {
            return (mask & this.getMask()) == this.getMask();
        }

        public static Set<ClientboundDefaultSpawnPacket.RelativeArgument> unpack(int mask) {
            Set<ClientboundDefaultSpawnPacket.RelativeArgument> set = EnumSet.noneOf(ClientboundDefaultSpawnPacket.RelativeArgument.class);

            for(ClientboundDefaultSpawnPacket.RelativeArgument relativeArgument : values()) {
                if (relativeArgument.isSet(mask)) {
                    set.add(relativeArgument);
                }
            }

            return set;
        }

        public static int pack(Set<ClientboundDefaultSpawnPacket.RelativeArgument> flags) {
            int i = 0;

            for(ClientboundDefaultSpawnPacket.RelativeArgument relativeArgument : flags) {
                i |= relativeArgument.getMask();
            }

            return i;
        }
    }
}
