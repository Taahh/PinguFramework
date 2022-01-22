package com.taahyt.pingu.util;

import com.google.common.collect.Maps;
import com.taahyt.pingu.block.Block;
import com.taahyt.pingu.game.EventTypes;
import com.taahyt.pingu.item.Item;
import com.taahyt.pingu.util.registry.Registries;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class EnumUtil
{

    public static void generateEnumMaterial(File file)
    {
        if (!file.exists())
        {
            try
            {
                file.createNewFile();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        Map<String, Integer> MATERIALS = Maps.newHashMap();
        Registries.ITEMS.forEach(item -> MATERIALS.put(item.getIdentifier(), item.getId()));
        Registries.BLOCKS.forEach(block -> MATERIALS.put(block.getIdentifier(), block.getId()));

        SortedSet<String> keys = new TreeSet<>(MATERIALS.keySet());

        try (FileWriter writer = new FileWriter(file))
        {
            for (String key : keys)
            {
                int id = MATERIALS.get(key);
                if (key.equalsIgnoreCase(keys.last()))
                {
                    writer.write(String.format("%s(%s, %s);", key.replace("minecraft:", "").toUpperCase(Locale.ROOT), id, Registries.BLOCKS.stream().map(Block::getIdentifier).collect(Collectors.toList()).contains(key)));
                } else {
                    writer.write(String.format("%s(%s, %s),", key.replace("minecraft:", "").toUpperCase(Locale.ROOT), id, Registries.BLOCKS.stream().map(Block::getIdentifier).collect(Collectors.toList()).contains(key)));
                    writer.write("\n");
                }
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static void generateEnumEntityType(File file)
    {
        if (!file.exists())
        {
            try
            {
                file.createNewFile();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        Map<String, Integer> ENTITY_TYPES = Maps.newHashMap();
        Registries.ENTITY_TYPES.forEach(entityType -> ENTITY_TYPES.put(entityType.getIdentifier(), entityType.getId()));

        SortedSet<String> keys = new TreeSet<>(ENTITY_TYPES.keySet());

        try (FileWriter writer = new FileWriter(file))
        {
            for (String key : keys)
            {
                int id = ENTITY_TYPES.get(key);
                if (key.equalsIgnoreCase(keys.last()))
                {
                    writer.write(String.format("%s(%s);", key.replace("minecraft:", "").toUpperCase(Locale.ROOT), id));
                } else {
                    writer.write(String.format("%s(%s),", key.replace("minecraft:", "").toUpperCase(Locale.ROOT), id));
                    writer.write("\n");
                }
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static void generateEnumGameEvents(File file)
    {
        if (!file.exists())
        {
            try
            {
                file.createNewFile();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        Map<String, Integer> GAME_EVENTS = Maps.newHashMap();
        Registries.GAME_EVENTS.forEach(gameEvent -> GAME_EVENTS.put(gameEvent.getIdentifier(), gameEvent.getId()));

        SortedSet<String> keys = new TreeSet<>(GAME_EVENTS.keySet());

        try (FileWriter writer = new FileWriter(file))
        {
            for (String key : keys)
            {
                int id = GAME_EVENTS.get(key);
                if (key.equalsIgnoreCase(keys.last()))
                {
                    writer.write(String.format("%s(%s);", key.replace("minecraft:", "").toUpperCase(Locale.ROOT), id));
                } else {
                    writer.write(String.format("%s(%s),", key.replace("minecraft:", "").toUpperCase(Locale.ROOT), id));
                    writer.write("\n");
                }
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

     public static void generateEnumFluids(File file)
    {
        if (!file.exists())
        {
            try
            {
                file.createNewFile();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        Map<String, Integer> FLUIDS = Maps.newHashMap();
        Registries.FLUIDS.forEach(fluid -> FLUIDS.put(fluid.getIdentifier(), fluid.getId()));

        SortedSet<String> keys = new TreeSet<>(FLUIDS.keySet());

        try (FileWriter writer = new FileWriter(file))
        {
            for (String key : keys)
            {
                int id = FLUIDS.get(key);
                if (key.equalsIgnoreCase(keys.last()))
                {
                    writer.write(String.format("%s(%s);", key.replace("minecraft:", "").toUpperCase(Locale.ROOT), id));
                } else {
                    writer.write(String.format("%s(%s),", key.replace("minecraft:", "").toUpperCase(Locale.ROOT), id));
                    writer.write("\n");
                }
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

}
