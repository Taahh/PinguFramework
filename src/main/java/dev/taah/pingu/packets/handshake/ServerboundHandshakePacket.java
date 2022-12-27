package dev.taah.pingu.packets.handshake;

import dev.taah.pingu.PinguFramework;
import dev.taah.pingu.client.Status;
import dev.taah.pingu.handler.PacketBuffer;
import dev.taah.pingu.packets.AbstractPacket;
import dev.taah.pingu.util.Logger;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public class ServerboundHandshakePacket extends AbstractPacket
{

    @Override
    public void deserialize(ChannelHandlerContext channel, PacketBuffer buf)
    {
        int protocolVersion = buf.readVarInt();
        String address = buf.readString();
        int port = buf.readUnsignedShort();
        int state = buf.readVarInt();

        if (state == 1) {
            PinguFramework.CLIENTS.put(channel, Status.STATUS);
        } else if (state == 2) {
            PinguFramework.CLIENTS.put(channel, Status.LOGIN);
        }

        Logger.log("Incoming handshake with protocol version {0}, address {1}:{2}, and state {3}", protocolVersion, address, port, state);
    }

    @Override
    public ByteBuf serialize(ChannelHandlerContext channel)
    {
        return null;
    }
}
