package dev.taah.pingu.world;

import lombok.Data;

@Data
public class Location
{
    private final World world;
    private double x, y, z;
    private float yaw, pitch;

    public Location(World world, double x, double y, double z)
    {
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
    }


//    public static Location deserialize()
}
