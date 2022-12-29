package dev.taah.pingu.packets.status;

import dev.taah.pingu.handler.PacketBuffer;
import dev.taah.pingu.packets.AbstractPacket;
import dev.taah.pingu.util.Logger;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.java.Log;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import org.json.JSONArray;
import org.json.JSONObject;

public class ClientboundStatusPacket extends AbstractPacket
{

    @Override
    public void deserialize(ChannelHandlerContext channel, PacketBuffer buf)
    {
    }

    @Override
    public ByteBuf serialize(ChannelHandlerContext channel)
    {
        PacketBuffer buffer = new PacketBuffer();
        JSONObject object = new JSONObject();
        object.put("version", new JSONObject());
        ((JSONObject)object.get("version")).put("name", "1.19.3");
        ((JSONObject)object.get("version")).put("protocol", 761);

        object.put("players", new JSONObject());
        ((JSONObject)object.get("players")).put("max", 100);
        ((JSONObject)object.get("players")).put("online", 99);
        ((JSONObject)object.get("players")).put("sample", new JSONArray());

        object.put("description", new JSONObject(GsonComponentSerializer.gson().serialize(MiniMessage.miniMessage().deserialize("<red>Test"))));
        object.put("previewsChat", false);
        object.put("enforcesSecureChat", false);

        buffer.writeVarInt(0x00);
        buffer.writeString(object.toString().replace("\\", ""));
        return buffer;
    }
}
