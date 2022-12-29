package dev.taah.pingu.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.CorruptedFrameException;

import java.util.List;

/**
 * Yoinked from MC's Game Code
 *
 * @author mojang
 */

public class Varint21FrameDecoder extends ByteToMessageDecoder
{
    private final byte[] lenBuf = new byte[3]; // Paper
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) {
        // Paper start - if channel is not active just discard the packet
        if (!channelHandlerContext.channel().isActive()) {
            byteBuf.skipBytes(byteBuf.readableBytes());
            return;
        }
        // Paper end
        byteBuf.markReaderIndex();
        // Paper start - reuse temporary length buffer
        byte[] bs = lenBuf;
        java.util.Arrays.fill(bs, (byte) 0);
        // Paper end

        for(int i = 0; i < bs.length; ++i) {
            if (!byteBuf.isReadable()) {
                byteBuf.resetReaderIndex();
                return;
            }

            bs[i] = byteBuf.readByte();
            if (bs[i] >= 0) {
                PacketBuffer friendlyByteBuf = new PacketBuffer(Unpooled.wrappedBuffer(bs));

                try {
                    int j = friendlyByteBuf.readVarInt();
                    if (byteBuf.readableBytes() >= j) {
                        list.add(byteBuf.readBytes(j));
                        return;
                    }

                    byteBuf.resetReaderIndex();
                } finally {
                    friendlyByteBuf.release();
                }

                return;
            }
        }

        throw new CorruptedFrameException("length wider than 21-bit");
    }
}