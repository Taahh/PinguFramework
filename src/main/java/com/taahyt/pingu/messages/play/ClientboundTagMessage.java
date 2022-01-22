package com.taahyt.pingu.messages.play;

import com.google.common.collect.Lists;
import com.taahyt.pingu.messages.AbstractMessage;
import com.taahyt.pingu.util.packet.PacketBuffer;
import com.taahyt.pingu.util.tags.Tag;
import com.taahyt.pingu.util.tags.TagUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.util.List;

public class ClientboundTagMessage extends AbstractMessage
{
    public ClientboundTagMessage()
    {
        super(0x67);
    }

    @Override
    public void deserialize(ChannelHandlerContext channel, PacketBuffer buf)
    {

    }

    @Override
    public ByteBuf serialize(ChannelHandlerContext channel)
    {
        System.out.println("Tags!");
        PacketBuffer buffer = new PacketBuffer();
        buffer.writeVarInt(this.getPacketId());

        List<Tag> tags = Lists.newArrayList();

        /*Tag tag = new Tag("minecraft:block");
        tag.getChildren().addAll(TagUtil.BLOCK_TAGS);
        tag.getChildren().addAll(TagUtil.ITEM_TAGS);
        tag.getChildren().addAll(TagUtil.GAME_EVENTS);
        tag.getChildren().addAll(TagUtil.ENTITY_TAGS);
        tag.getChildren().addAll(TagUtil.FLUIDS);
        tags.add(tag);

        buffer.writeCollection(tags, (packetBuffer, tag1) -> {
            System.out.println("Tag 1" + tag1.getName());
            //tag1 is minecraft:block etc.
            packetBuffer.writeCollection(tag1.getChildren(), (packetBuffer1, tag2) -> {
                //tag2 is like actual tag names
                System.out.println("Tag2: " + tag2.getName());
                tag2.write(buffer);
            });
        });
*/
        Tag block = new Tag("minecraft:block");
        block.getChildren().addAll(TagUtil.BLOCK_TAGS);
        tags.add(block);

        Tag fluids = new Tag("minecraft:fluid");
        fluids.getChildren().addAll(TagUtil.FLUIDS);
        tags.add(fluids);

        Tag item = new Tag("minecraft:item");
        item.getChildren().addAll(TagUtil.ITEM_TAGS);
        tags.add(item);

        Tag entityTypes = new Tag("minecraft:entity_type");
        entityTypes.getChildren().addAll(TagUtil.ENTITY_TAGS);
        tags.add(entityTypes);

        Tag gameEvent = new Tag("minecraft:game_event");
        gameEvent.getChildren().addAll(TagUtil.GAME_EVENTS);
        tags.add(gameEvent);

        buffer.writeCollection(tags, (packetBuffer, tag) -> {
            packetBuffer.writeString(tag.getName());
            packetBuffer.writeCollection(tag.getChildren(), (packetBuffer1, tag1) -> {
                tag1.write(packetBuffer);
            });
        });

        return buffer;
    }

}
