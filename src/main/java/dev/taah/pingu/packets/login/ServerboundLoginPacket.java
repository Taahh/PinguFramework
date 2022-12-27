package dev.taah.pingu.packets.login;

import dev.taah.pingu.PinguFramework;
import dev.taah.pingu.handler.PacketBuffer;
import dev.taah.pingu.packets.AbstractPacket;
import dev.taah.pingu.util.Logger;
import dev.taah.pingu.util.Profile;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.util.Arrays;
import java.util.UUID;

public class ServerboundLoginPacket extends AbstractPacket
{
    private UUID uuid;
    @Override
    public void deserialize(ChannelHandlerContext channel, PacketBuffer buf)
    {
        String name = buf.readString();
        boolean hasData = buf.readBoolean();
        Logger.log("Name: {0}; has data? {1}", name, hasData);
        long timestamp;
//        int keyLength, sigLength;
        byte[] key, sig;
        boolean hasUuid;
        UUID uuid;
        if (hasData) {
            timestamp = buf.readLong();
            key = buf.readByteArray();
            sig = buf.readByteArray();
            hasUuid = buf.readBoolean();
            Logger.log("Timestamp: {0}, \nKey: {1}, \nSig: {2}, \nhas UUID? {3}", timestamp, Arrays.toString(key), Arrays.toString(sig), hasUuid);
            if (hasUuid) {
                uuid = buf.readUUID();
                Logger.log("UUID: {0}", uuid);
                PinguFramework.SERVER.getPlayer(uuid).setProfile(new Profile(uuid, name));
                this.uuid = uuid;
                channel.writeAndFlush(new ClientboundLoginPacket(uuid).serialize(channel));
                channel.writeAndFlush(new dev.taah.pingu.packets.play.ClientboundLoginPacket(PinguFramework.SERVER.getPlayer(uuid)).serialize(channel));
            }
        }


    }

    @Override
    public ByteBuf serialize(ChannelHandlerContext channel)
    {
        return null;
    }
}
