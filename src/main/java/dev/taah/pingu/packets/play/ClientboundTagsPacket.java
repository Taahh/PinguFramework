package dev.taah.pingu.packets.play;

import dev.taah.pingu.PinguFramework;
import dev.taah.pingu.handler.PacketBuffer;
import dev.taah.pingu.packets.AbstractPacket;
import dev.taah.pingu.server.internals.Identifier;
import dev.taah.pingu.server.internals.Tag;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class ClientboundTagsPacket extends AbstractPacket
{

    @Override
    public void deserialize(ChannelHandlerContext channel, PacketBuffer buf)
    {

    }

    @Override
    public ByteBuf serialize(ChannelHandlerContext channel)
    {

        final PacketBuffer buffer = new PacketBuffer();

        buffer.writeVarInt(0x6A);
        Map<Identifier, List<Tag>> tags = PinguFramework.SERVER.getLoader().getTags();
        buffer.writeVarInt(tags.size());
        tags.forEach((identifier, tags1) -> {
            buffer.writeString(identifier.toString());
            buffer.writeCollection(tags1, (packetBuffer, tag) -> {
                buffer.writeString(tag.getIdentifier().toString());
                buffer.writeCollection(tag.getEntries(), (packetBuffer1, data) -> {
                    packetBuffer1.writeVarInt(data.getProtocolId());
                });
            });
        });

        return buffer;
    }
}
