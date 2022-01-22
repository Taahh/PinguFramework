package com.taahyt.pingu.fluid;

import java.util.Arrays;

public enum Fluids
{

    EMPTY(0),
    FLOWING_LAVA(3),
    FLOWING_WATER(1),
    LAVA(4),
    WATER(2);

    private final int id;

    Fluids(int id)
    {
        this.id = id;
    }

    public int getId()
    {
        return this.id;
    }

    public String getIdentifier()
    {
        return "minecraft:" + this.name().toLowerCase();
    }

    public static Fluids getByIdentifier(String identifier)
    {
        return Arrays.stream(values()).filter(mat -> mat.getIdentifier().equalsIgnoreCase(identifier)).findFirst().orElse(null);
    }

    public static Fluids getById(int id)
    {
        return Arrays.stream(values()).filter(mat -> mat.getId() == id).findFirst().orElse(null);
    }

}
