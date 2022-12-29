package dev.taah.pingu.file;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Queues;
import com.google.gson.GsonBuilder;
import dev.taah.pingu.server.internals.Identifier;
import dev.taah.pingu.server.internals.Tag;
import dev.taah.pingu.util.IOUtil;
import dev.taah.pingu.util.Logger;
import lombok.Getter;
import net.kyori.adventure.nbt.BinaryTagIO;
import net.kyori.adventure.nbt.CompoundBinaryTag;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;

public class DataLoader
{

    private static final String VERSION = "1.19.3";
    private static final File DATA_FOLDER = new File("data");

    @Getter
    private final Map<Identifier, List<Tag>> tags = Maps.newHashMap();

    @Getter
    private final CompoundBinaryTag registryCodec;

    @Getter
    private final JSONObject tagsJson;

    static
    {
        if (!DATA_FOLDER.exists())
        {
            DATA_FOLDER.mkdir();
        }
    }

    public DataLoader()
    {
        try
        {
            this.registryCodec = BinaryTagIO.unlimitedReader().read(new URL("https://minecraft.taah.dev/registryCodec-" + VERSION + ".nbt").openStream(), BinaryTagIO.Compression.GZIP);
            this.tagsJson = new JSONObject(IOUtil.readUrl("https://minecraft.taah.dev/" + VERSION + "/tags.json"));

            this.loadTags();

            this.tags.values().forEach(tags1 -> tags1.forEach(this::fixTag));
            this.removeHashTags();

            File file = new File(DATA_FOLDER, "tags.json");
            if (!file.exists())
            {
                file.createNewFile();
            }
            try (FileWriter writer = new FileWriter(file))
            {
                writer.write(new GsonBuilder().setPrettyPrinting().create().toJson(this.tags));
                writer.flush();
            }
        } catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    private void loadTags()
    {
        this.tagsJson.keySet().forEach(key ->
        { //get each key
            final Identifier identifier = Identifier.of(key);
            List<Tag> list = Lists.newArrayList();
            JSONArray array = this.tagsJson.getJSONArray(key);
            array.forEach(o ->
            { //get each tag
                JSONObject object = new JSONObject(o.toString());
                Tag tag = new Tag(identifier, new Identifier("minecraft", object.getString("name")));
                JSONArray values = object.getJSONArray("values");
                values.forEach(o1 ->
                {
                    JSONObject obj = new JSONObject(o1.toString());
                    tag.getEntries().add(new Tag.Data(Identifier.of(obj.getString("key")), obj.isNull("id") ? -1 : obj.getInt("id")));
                });
                list.add(tag);
            });
            this.tags.put(identifier, list);
        });
    }

    private void fixTag(Tag tag)
    {
        for (int i = 0; i < tag.getEntries().size(); i++)
        {
            Tag.Data data = tag.getEntries().get(i);
            if (data.getIdentifier().getNamespace().startsWith("#"))
            {
                Tag find = tags.get(tag.getParentIdentifier()).stream().filter(tag1 -> tag1.getIdentifier().equals(data.getIdentifier())).findFirst().orElse(null);

                if (find == null)
                {
                    Logger.log("Couldn't find tag {0}", data.getIdentifier());
                } else {
                    fixTag(find);
                    tag.getEntries().addAll(find.getEntries());
                }
            }
        }
    }

    private void removeHashTags()
    {
        tags.values().forEach(tags1 -> {
            tags1.forEach(tag -> {
                Stack<Tag.Data> stack = new Stack<>();
                tag.getEntries().forEach(data -> {
                    if (data.getIdentifier().getNamespace().startsWith("#"))
                    {
                        stack.push(data);
                    }
                });
                while (!stack.isEmpty())
                {
                    Tag.Data data = stack.pop();
                    tag.getEntries().remove(data);
                }
            });
        });
    }


}
