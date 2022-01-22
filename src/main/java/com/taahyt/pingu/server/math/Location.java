package com.taahyt.pingu.server.math;

import com.taahyt.pingu.server.World;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Location
{
    private final World world;
    private double x;
    private double y;
    private double z;

    private float yaw;
    private float pitch;

    private Location() {
        this(null, -1, -1, -1);
    }

    public Location(World world, double x, double y, double z)
    {
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;

        this.yaw = 180;
        this.pitch = 0;
    }
}
