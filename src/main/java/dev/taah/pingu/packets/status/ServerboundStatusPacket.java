package dev.taah.pingu.packets.status;

import dev.taah.pingu.handler.PacketBuffer;
import dev.taah.pingu.packets.AbstractPacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import org.json.JSONArray;
import org.json.JSONObject;

public class ServerboundStatusPacket extends AbstractPacket
{

    @Override
    public void deserialize(ChannelHandlerContext channel, PacketBuffer buf)
    {
    }

    @Override
    public ByteBuf serialize(ChannelHandlerContext channel)
    {
        return new ClientboundStatusPacket().serialize(channel);
    }
}
