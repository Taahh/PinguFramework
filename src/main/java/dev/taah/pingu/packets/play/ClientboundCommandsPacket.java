package dev.taah.pingu.packets.play;

import com.google.common.collect.Lists;
import com.google.common.collect.Queues;
import com.mojang.brigadier.tree.RootCommandNode;
import dev.taah.pingu.handler.PacketBuffer;
import dev.taah.pingu.packets.AbstractPacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.tree.ArgumentCommandNode;
import com.mojang.brigadier.tree.CommandNode;
import com.mojang.brigadier.tree.LiteralCommandNode;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.Queue;

@RequiredArgsConstructor
public class ClientboundCommandsPacket extends AbstractPacket
{

    @Override
    public void deserialize(ChannelHandlerContext channel, PacketBuffer buf)
    {

    }

    @Override
    public ByteBuf serialize(ChannelHandlerContext channel)
    {

        final PacketBuffer buffer = new PacketBuffer();

        buffer.writeVarInt(0x0E);

        RootCommandNode<SuggestionProvider<Object>> root = new RootCommandNode<>();

        List<CommandNode<SuggestionProvider<Object>>> nodes = Lists.newArrayList();
        nodes.add(root);

        Object2IntMap<CommandNode<SuggestionProvider<Object>>> object2IntMap = enumerateNodes(root);

        buffer.writeCollection(nodes, (packetBuffer, objectCommandNode) ->
                writeNode(packetBuffer, objectCommandNode, object2IntMap));
        buffer.writeVarInt(object2IntMap.getInt(root));

        return buffer;
    }

    /**
     * Yoinked from MC's Game Code
     * @author mojang
     */
    private static Object2IntMap<CommandNode<SuggestionProvider<Object>>> enumerateNodes(RootCommandNode<SuggestionProvider<Object>> commandTree)
    {
        Object2IntMap<CommandNode<SuggestionProvider<Object>>> object2IntMap = new Object2IntOpenHashMap<>();
        Queue<CommandNode<SuggestionProvider<Object>>> queue = Queues.newArrayDeque();
        queue.add(commandTree);

        CommandNode<SuggestionProvider<Object>> commandNode;
        while ((commandNode = queue.poll()) != null)
        {
            if (!object2IntMap.containsKey(commandNode))
            {
                int i = object2IntMap.size();
                object2IntMap.put(commandNode, i);
                queue.addAll(commandNode.getChildren());
                if (commandNode.getRedirect() != null)
                {
                    queue.add(commandNode.getRedirect());
                }
            }
        }

        return object2IntMap;
    }

    /**
     * Yoinked from MC's Game Code
     * @author mojang
     */
    private static void writeNode(PacketBuffer buf, CommandNode<SuggestionProvider<Object>> node, Map<CommandNode<SuggestionProvider<Object>>, Integer> nodeToIndex)
    {
        byte b = 0;
        if (node.getRedirect() != null)
        {
            b = (byte) (b | 8);
        }

        if (node.getCommand() != null)
        {
            b = (byte) (b | 4);
        }

        if (node instanceof ArgumentCommandNode)
        {
            b = (byte) (b | 2);
            if (((ArgumentCommandNode) node).getCustomSuggestions() != null)
            {
                b = (byte) (b | 16);
            }
        } else
        {
            if (!(node instanceof LiteralCommandNode))
            {
                throw new UnsupportedOperationException("Unknown node type " + node);
            }

            b = (byte) (b | 1);
        }

        buf.writeByte(b);
        buf.writeVarInt(node.getChildren().size());

        for (CommandNode<SuggestionProvider<Object>> commandNode : node.getChildren())
        {
            buf.writeVarInt(nodeToIndex.get(commandNode));
        }

        if (node.getRedirect() != null)
        {
            buf.writeVarInt(nodeToIndex.get(node.getRedirect()));
        }

//        if (node instanceof ArgumentCommandNode) {
//            ArgumentCommandNode<SuggestionProvider<Object>, ?> argumentCommandNode = (ArgumentCommandNode)node;
//            buf.writeUtf(argumentCommandNode.getName());
//            ArgumentTypes.serialize(buf, argumentCommandNode.getType());
//            if (argumentCommandNode.getCustomSuggestions() != null) {
//                buf.writeResourceLocation(SuggestionProviders.getName(argumentCommandNode.getCustomSuggestions()));
//            }
//        } else if (node instanceof LiteralCommandNode) {
//            buf.writeUtf(((LiteralCommandNode)node).getLiteral());
//        }

    }
}
