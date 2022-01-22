package com.taahyt.pingu.messages.handshaking;

import com.taahyt.pingu.PinguFramework;
import com.taahyt.pingu.messages.AbstractMessage;
import com.taahyt.pingu.util.packet.PacketBuffer;
import com.taahyt.pingu.util.Status;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.SneakyThrows;


public class ServerboundHandshakeMessage extends AbstractMessage
{
    public ServerboundHandshakeMessage()
    {
        super(0x00);
    }

    @SneakyThrows
    @Override
    public void deserialize(ChannelHandlerContext channel, PacketBuffer buf)
    {
        System.out.println("Readable Bytes:" + buf.readableBytes());

        System.out.println("Deserializing Handshake");

        int protocolVersion = buf.readVarInt();
        String serverAddress = buf.readString();
        int port = buf.readUnsignedShort();
        int nextState = buf.readVarInt();
        System.out.println("Protocol Version: " + protocolVersion);
        System.out.println("Server Address: " + serverAddress);
        System.out.println("Server port: " + port);
        System.out.println("Next State: " + nextState);
        switch (nextState) {
            case 0x01 -> PinguFramework.CLIENTS.put(channel, Status.STATUS);
            case 0x02 -> PinguFramework.CLIENTS.put(channel, Status.LOGIN);
            default -> PinguFramework.CLIENTS.remove(channel);
        }

    }

    @Override
    public ByteBuf serialize(ChannelHandlerContext channel)
    {
        return null;
    }
}
