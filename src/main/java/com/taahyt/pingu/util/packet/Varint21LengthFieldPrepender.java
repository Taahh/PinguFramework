package com.taahyt.pingu.util.packet;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class Varint21LengthFieldPrepender extends MessageToByteEncoder<ByteBuf>
{

    private static final int MAX_BYTES = 3;

    protected void encode(ChannelHandlerContext p_130571_, ByteBuf p_130572_, ByteBuf p_130573_)
    {
        int i = p_130572_.readableBytes();
        int j = PacketBuffer.getVarIntSize(i);
        if (j > 3) {
            throw new IllegalArgumentException("unable to fit " + i + " into 3");
        } else {
            PacketBuffer packetBuffer = new PacketBuffer(p_130573_);
            packetBuffer.ensureWritable(j + i);
            packetBuffer.writeVarInt(i);
            packetBuffer.writeBytes(p_130572_, p_130572_.readerIndex(), i);

            //System.out.println("Sending packet: " + ByteBufUtil.prettyHexDump(packetBuffer));
        }
    }

}
