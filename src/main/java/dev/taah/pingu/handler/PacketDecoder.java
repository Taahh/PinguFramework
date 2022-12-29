package dev.taah.pingu.handler;

import dev.taah.pingu.PinguFramework;
import dev.taah.pingu.client.Status;
import dev.taah.pingu.packets.AbstractPacket;
import dev.taah.pingu.packets.Packets;
import dev.taah.pingu.util.Logger;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class PacketDecoder extends ByteToMessageDecoder
{

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception
    {
        int i = in.readableBytes();
        if (i != 0) {
            PacketBuffer packetBuffer = new PacketBuffer(in);
            int j = packetBuffer.readVarInt();
            System.out.println("Incoming packet with length " + i + " and ID " + j);
            System.out.println(ByteBufUtil.prettyHexDump(packetBuffer));

            Status status = PinguFramework.CLIENTS.getOrDefault(ctx, Status.HANDSHAKE);

            AbstractPacket message = Packets.getById(status, j);
            if (message != null) {
                System.out.println("Incoming packet with length " + i + " and ID " + j);
                System.out.println("USING " + message.getClass().getName() + " PACKET");
                if (packetBuffer.readableBytes() >= 1) {
                    message.deserialize(ctx, packetBuffer);
                }

                ByteBuf serializedMsg = message.serialize(ctx);
                if (serializedMsg != null) ctx.writeAndFlush(serializedMsg);
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception
    {
        try
        {
            throw cause;
        } catch (Throwable e)
        {
            throw new RuntimeException(e);
        }
    }
}
