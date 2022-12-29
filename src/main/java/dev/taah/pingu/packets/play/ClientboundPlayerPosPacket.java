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
public class ClientboundPlayerPosPacket extends AbstractPacket
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

        buffer.writeVarInt(0x38);
        buffer.writeDouble(location.getX());
        buffer.writeDouble(location.getY());
        buffer.writeDouble(location.getZ());
        buffer.writeFloat(location.getYaw());
        buffer.writeFloat(location.getPitch());
        buffer.writeByte(RelativeArgument.pack(RelativeArgument.ALL));
        buffer.writeVarInt(0);
        buffer.writeBoolean(true);

        return buffer;
    }

    public enum RelativeArgument {
        X(0),
        Y(1),
        Z(2),
        Y_ROT(3),
        X_ROT(4);

        public static final Set<ClientboundPlayerPosPacket.RelativeArgument> ALL = Set.of(values());
        public static final Set<ClientboundPlayerPosPacket.RelativeArgument> ROTATION = Set.of(X_ROT, Y_ROT);
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

        public static Set<ClientboundPlayerPosPacket.RelativeArgument> unpack(int mask) {
            Set<ClientboundPlayerPosPacket.RelativeArgument> set = EnumSet.noneOf(ClientboundPlayerPosPacket.RelativeArgument.class);

            for(ClientboundPlayerPosPacket.RelativeArgument relativeArgument : values()) {
                if (relativeArgument.isSet(mask)) {
                    set.add(relativeArgument);
                }
            }

            return set;
        }

        public static int pack(Set<ClientboundPlayerPosPacket.RelativeArgument> flags) {
            int i = 0;

            for(ClientboundPlayerPosPacket.RelativeArgument relativeArgument : flags) {
                i |= relativeArgument.getMask();
            }

            return i;
        }
    }
}
