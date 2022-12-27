package dev.taah.pingu.entity;

import dev.taah.pingu.world.Location;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
@Getter
public abstract class Entity
{
    private final UUID uuid;
    private final int id;
    private Location location;
}
