/*
package com.taahyt.pingu.handler;

import com.taahyt.pingu.messages.AbstractMessage;
import com.taahyt.pingu.messages.Messages;
import com.taahyt.pingu.util.PacketBuffer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.compression.JZlibDecoder;
import io.netty.handler.codec.compression.ZlibCodecFactory;
import lombok.SneakyThrows;

import java.nio.charset.StandardCharsets;
import java.util.zip.Deflater;
import java.util.zip.Inflater;


public class PacketHandler extends SimpleChannelInboundHandler<ByteBuf>
{
    @SneakyThrows
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg)
    {
        System.out.println("Incoming from: " + ctx.channel().remoteAddress());

        final PacketBuffer buffer = new PacketBuffer(msg);
        int length = buffer.readVarInt();
        int packetId = buffer.readVarInt();

        System.out.println("Incoming packet with length " + length + " and packet ID " + packetId);
        System.out.println(ByteBufUtil.prettyHexDump(msg));

        PacketBuffer setCompression = new PacketBuffer();
        setCompression.writeVarInt(0x03);
        setCompression.writeVarInt(-1);
        setCompression.resetWriterIndex();
        setCompression.writeVarInt(setCompression.getByteArray().length);
        ctx.write(setCompression);


        AbstractMessage message = Messages.getById(packetId);
        if (message != null)
        {
            System.out.println("USING " + message.getClass().getName() + " PACKET");
            message.deserialize(ctx, buffer);

            ByteBuf serializedMsg = message.serialize(ctx);
            if (serializedMsg != null) ctx.writeAndFlush(serializedMsg).sync();
        } else {
            ctx.channel().pipeline().addLast("inflater", ZlibCodecFactory.newZlibDecoder());
            packetId = buffer.readVarInt();
            System.out.println("Compressed Packet? " + packetId);
        }
    }
}
*/
