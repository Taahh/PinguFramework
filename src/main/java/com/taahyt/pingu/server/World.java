package com.taahyt.pingu.server;

import com.google.common.collect.Maps;
import com.taahyt.pingu.entity.Entity;
import lombok.Data;
import net.querz.nbt.tag.CompoundTag;

import java.util.Map;

@Data
public class World
{
    private final String name;
    private final Map<Integer, Entity> entities = Maps.newHashMap();

    private CompoundTag dimensionType;

    public int addEntity(Entity entity)
    {
        int id = entities.size();
        this.entities.put(id, entity);
        entity.setId(id);
        return id;
    }
}
