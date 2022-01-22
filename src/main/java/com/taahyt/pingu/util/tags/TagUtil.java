package com.taahyt.pingu.util.tags;

import com.google.common.collect.Lists;
import com.taahyt.pingu.entity.EntityTypes;
import com.taahyt.pingu.fluid.Fluid;
import com.taahyt.pingu.fluid.Fluids;
import com.taahyt.pingu.game.EventTypes;
import com.taahyt.pingu.material.Material;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

public class TagUtil
{

    public static List<Tag> ENTITY_TAGS = Lists.newArrayList();
    public static List<Tag> ITEM_TAGS = Lists.newArrayList();
    public static List<Tag> BLOCK_TAGS = Lists.newArrayList();
    public static List<Tag> FLUIDS = Lists.newArrayList();
    public static List<Tag> GAME_EVENTS = Lists.newArrayList();

    public static void loadAllBlocks(File parentDir)
    {
        for (File file : parentDir.listFiles())
        {
            if (file.getName().equalsIgnoreCase("mineable"))
            {
                for (File mineables : new File(parentDir + File.separator + "mineable").listFiles())
                {
                    try (FileInputStream fis = new FileInputStream(mineables))
                    {
                        final JSONTokener tokener = new JSONTokener(fis);
                        final JSONObject object = new JSONObject(tokener);

                        final JSONArray array = object.getJSONArray("values");
                        Tag tag = new Tag("mineable/" + mineables.getName().replace(".json", ""));
                        array.forEach(block ->
                        {
                            if (block.toString().startsWith("#"))
                            {
                                tag.getQueuedValues().add(block.toString());
                            } else
                            {
                                Material material = Material.getByIdentifier(block.toString());
                                tag.getValues().add(material.getId());
                            }
                        });
                        BLOCK_TAGS.add(tag);
                    } catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
            } else
            {
                try (FileInputStream fis = new FileInputStream(file))
                {
                    final JSONTokener tokener = new JSONTokener(fis);
                    final JSONObject object = new JSONObject(tokener);

                    final JSONArray array = object.getJSONArray("values");
                    Tag tag = new Tag(file.getName().replace(".json", ""));
                    array.forEach(block ->
                    {
                        if (block.toString().startsWith("#"))
                        {
                            tag.getQueuedValues().add(block.toString());
                        } else
                        {
                            Material material = Material.getByIdentifier(block.toString());
                            tag.getValues().add(material.getId());
                        }
                    });
                    BLOCK_TAGS.add(tag);
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void loadAllItems(File parentDir)
    {
        for (File file : parentDir.listFiles())
        {
            try (FileInputStream fis = new FileInputStream(file))
            {
                final JSONTokener tokener = new JSONTokener(fis);
                final JSONObject object = new JSONObject(tokener);

                final JSONArray array = object.getJSONArray("values");
                Tag tag = new Tag(file.getName().replace(".json", ""));
                array.forEach(block ->
                {
                    if (block.toString().startsWith("#"))
                    {
                        tag.getQueuedValues().add(block.toString());
                    } else
                    {
                        Material material = Material.getByIdentifier(block.toString());
                        tag.getValues().add(material.getId());
                    }
                });
                ITEM_TAGS.add(tag);
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    public static void loadAllEntities(File parentDir)
    {
        for (File file : parentDir.listFiles())
        {
            try (FileInputStream fis = new FileInputStream(file))
            {
                final JSONTokener tokener = new JSONTokener(fis);
                final JSONObject object = new JSONObject(tokener);

                final JSONArray array = object.getJSONArray("values");
                Tag tag = new Tag(file.getName().replace(".json", ""));
                array.forEach(entityType ->
                {
                    if (entityType.toString().startsWith("#"))
                    {
                        tag.getQueuedValues().add(entityType.toString());
                    } else
                    {
                        EntityTypes entityTypes = EntityTypes.getByIdentifier(entityType.toString());
                        tag.getValues().add(entityTypes.getId());
                    }
                });
                ENTITY_TAGS.add(tag);
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    public static void loadAllFluids(File parentDir)
    {
        for (File file : parentDir.listFiles())
        {
            try (FileInputStream fis = new FileInputStream(file))
            {
                final JSONTokener tokener = new JSONTokener(fis);
                final JSONObject object = new JSONObject(tokener);

                final JSONArray array = object.getJSONArray("values");
                Tag tag = new Tag(file.getName().replace(".json", ""));
                array.forEach(fluid ->
                {
                    if (fluid.toString().startsWith("#"))
                    {
                        tag.getQueuedValues().add(fluid.toString());
                    } else
                    {
                        Fluids fluids = Fluids.getByIdentifier(fluid.toString());
                        tag.getValues().add(fluids.getId());
                    }
                });
                FLUIDS.add(tag);
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    public static void loadAllGameEvents(File parentDir)
    {
        for (File file : parentDir.listFiles())
        {
            try (FileInputStream fis = new FileInputStream(file))
            {
                final JSONTokener tokener = new JSONTokener(fis);
                final JSONObject object = new JSONObject(tokener);

                final JSONArray array = object.getJSONArray("values");
                Tag tag = new Tag(file.getName().replace(".json", ""));
                array.forEach(event ->
                {
                    if (event.toString().startsWith("#"))
                    {
                        tag.getQueuedValues().add(event.toString());
                    } else
                    {
                        EventTypes eventTypes = EventTypes.getByIdentifier(event.toString());
                        tag.getValues().add(eventTypes.getId());
                    }
                });
                GAME_EVENTS.add(tag);
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    public static void fixedQueues()
    {
        List<Tag> tags = Lists.newArrayList(BLOCK_TAGS);
        tags.addAll(ITEM_TAGS);

        for (Tag tag : BLOCK_TAGS)
        {
            if (!tag.getQueuedValues().isEmpty())
            {
                for (String value : tag.getQueuedValues())
                {
                    value = value.replace("#", "").replace("minecraft:", "");
                    String finalValue = value;
                    tags.stream().filter(query -> query.getName().equalsIgnoreCase(finalValue)).findFirst().ifPresentOrElse(query ->
                    {
                        tag.getValues().addAll(query.getValues());
                    }, () -> System.out.println("NOT PRESENT"));
                }
            }
        }
    }

}
