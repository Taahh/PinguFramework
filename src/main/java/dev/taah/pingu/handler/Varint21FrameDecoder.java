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
    protected void decode(ChannelHandlerContext p_130566_, ByteBuf p_130567_, List<Object> p_130568_) {
        p_130567_.markReaderIndex();
        byte[] abyte = new byte[3];

        for(int i = 0; i < abyte.length; ++i) {
            if (!p_130567_.isReadable()) {
                p_130567_.resetReaderIndex();
                return;
            }

            abyte[i] = p_130567_.readByte();
            if (abyte[i] >= 0) {
                PacketBuffer packetBuffer = new PacketBuffer(Unpooled.wrappedBuffer(abyte));

                try {
                    int j = packetBuffer.readVarInt();
                    if (p_130567_.readableBytes() >= j) {
                        p_130568_.add(p_130567_.readBytes(j));
                        return;
                    }

                    p_130567_.resetReaderIndex();
                } finally {
                    packetBuffer.release();
                }

                return;
            }
        }

        throw new CorruptedFrameException("length wider than 21-bit");
    }
}