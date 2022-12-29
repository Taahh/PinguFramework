package dev.taah.pingu.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Yoinked from MC's Game Code
 *
 * @author mojang
 */

public class Varint21LengthFieldPrepender extends MessageToByteEncoder<ByteBuf>
{

    private static final int MAX_BYTES = 3;

    protected void encode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, ByteBuf byteBuf2) {
        int i = byteBuf.readableBytes();
        int j = PacketBuffer.getVarIntSize(i);
        if (j > 3) {
            throw new IllegalArgumentException("unable to fit " + i + " into 3");
        } else {
            PacketBuffer friendlyByteBuf = new PacketBuffer(byteBuf2);
            friendlyByteBuf.ensureWritable(j + i);
            friendlyByteBuf.writeVarInt(i);
            friendlyByteBuf.writeBytes(byteBuf, byteBuf.readerIndex(), i);
        }
    }
}
