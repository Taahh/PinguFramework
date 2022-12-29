package dev.taah.pingu.packets.play;

import com.google.common.collect.Lists;
import com.google.common.collect.Queues;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.tree.ArgumentCommandNode;
import com.mojang.brigadier.tree.CommandNode;
import com.mojang.brigadier.tree.LiteralCommandNode;
import com.mojang.brigadier.tree.RootCommandNode;
import dev.taah.pingu.entity.Player;
import dev.taah.pingu.handler.PacketBuffer;
import dev.taah.pingu.packets.AbstractPacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import lombok.RequiredArgsConstructor;

import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.function.Consumer;

@RequiredArgsConstructor
public class ClientboundPlayerInfoUpdatePacket extends AbstractPacket
{
    private final EnumSet<Action> actions;
    private final List<Player> entries;
    @Override
    public void deserialize(ChannelHandlerContext channel, PacketBuffer buf)
    {

    }

    @Override
    public ByteBuf serialize(ChannelHandlerContext channel)
    {

        final PacketBuffer buffer = new PacketBuffer();

        buffer.writeVarInt(0x36);

        buffer.writeEnumSet(actions, Action.class);
        buffer.writeCollection(entries, (packetBuffer, player) -> {
            packetBuffer.writeUUID(player.getUuid());
            for (Action action : actions)
            {
                action.serializer.serialize(packetBuffer, player);
            }
        });

        return buffer;
    }

    public enum Action {
        ADD_PLAYER((buffer, player) -> {
            buffer.writeString(player.getProfile().getUsername());
            buffer.writeCollection(player.getProfile().getProperties(), (buf, property) -> {
                buf.writeString(property.getName());
                buf.writeString(property.getValue());
                buf.writeBoolean(property.isSigned());
                buf.writeString(property.getSignature());
            });
        }, buffer -> {

        }),
        INITIALIZE_CHAT((buffer, player) -> {

        }, buffer -> {

        }),
        UPDATE_GAME_MODE((buffer, player) -> {

        }, buffer -> {

        }),
        UPDATE_LISTED((buffer, player) -> {

        }, buffer -> {

        }),
        UPDATE_LATENCY((buffer, player) -> {
            buffer.writeVarInt(150);
        }, buffer -> {

        }),
        UPDATE_DISPLAY_NAME((buffer, player) -> {

        }, buffer -> {

        });

        private final Serializer serializer;
        private final Deserializer deserializer;
        Action(Serializer serializer, Deserializer deserializer)
        {
            this.serializer = serializer;
            this.deserializer = deserializer;
        }
    }

    private interface Serializer {
        void serialize(PacketBuffer buffer, Player player);
    }

    private interface Deserializer {
        void deserialize(PacketBuffer buffer);
    }
}
