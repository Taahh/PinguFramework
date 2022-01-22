package com.taahyt.pingu.util.registry;

import com.google.common.collect.Lists;
import com.taahyt.pingu.block.Block;
import com.taahyt.pingu.entity.EntityType;
import com.taahyt.pingu.fluid.Fluid;
import com.taahyt.pingu.game.GameEvent;
import com.taahyt.pingu.item.Item;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

public class Registries
{

    public static final List<Block> BLOCKS = Lists.newArrayList();
    public static final List<Item> ITEMS = Lists.newArrayList();
    public static final List<EntityType> ENTITY_TYPES = Lists.newArrayList();
    public static final List<GameEvent> GAME_EVENTS = Lists.newArrayList();
    public static final List<Fluid> FLUIDS = Lists.newArrayList();

    public static void registerAll(File file)
    {
        try (FileInputStream fis = new FileInputStream(file))
        {
            JSONTokener tokener = new JSONTokener(fis);
            JSONObject object = new JSONObject(tokener);
            for (String key : object.keySet())
            {
                JSONObject entries = object.getJSONObject(key).getJSONObject("entries");
                for (String entry : entries.keySet())
                {
                    if (key.equalsIgnoreCase("minecraft:block"))
                    {
                        Block block = new Block(entry);
                        block.setId(entries.getJSONObject(entry).getInt("protocol_id"));
                        BLOCKS.add(block);
                    } else if (key.equalsIgnoreCase("minecraft:item"))
                    {
                        Item item = new Item(entry);
                        item.setId(entries.getJSONObject(entry).getInt("protocol_id"));
                        ITEMS.add(item);
                    } else if (key.equalsIgnoreCase("minecraft:entity_type"))
                    {
                        EntityType entity = new EntityType(entry);
                        entity.setId(entries.getJSONObject(entry).getInt("protocol_id"));
                        ENTITY_TYPES.add(entity);
                    } else if (key.equalsIgnoreCase("minecraft:game_event"))
                    {
                        GameEvent gameEvent = new GameEvent(entry);
                        gameEvent.setId(entries.getJSONObject(entry).getInt("protocol_id"));
                        GAME_EVENTS.add(gameEvent);
                    } else if (key.equalsIgnoreCase("minecraft:fluid"))
                    {
                        Fluid fluid = new Fluid(entry);
                        fluid.setId(entries.getJSONObject(entry).getInt("protocol_id"));
                        FLUIDS.add(fluid);
                    }
                }
            }
            System.out.println("Added " + BLOCKS.size() + " blocks!");
            System.out.println("Added " + ITEMS.size() + " items!");
            System.out.println("Added " + ENTITY_TYPES.size() + " entity types!");
            System.out.println("Added " + GAME_EVENTS.size() + " game events!");
            System.out.println("Added " + FLUIDS.size() + " fluids!");
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
