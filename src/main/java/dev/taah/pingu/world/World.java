package dev.taah.pingu.world;

import com.google.common.collect.Maps;
import dev.taah.pingu.entity.Entity;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;

public class World
{
    private final Map<UUID, Entity> entities = Maps.newHashMap();

    public Collection<Entity> getEntities() {
        return entities.values();
    }
}
