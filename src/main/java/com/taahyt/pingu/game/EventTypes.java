package com.taahyt.pingu.game;

import java.util.Arrays;

public enum EventTypes
{

    BLOCK_ATTACH(0),
    BLOCK_CHANGE(1),
    BLOCK_CLOSE(2),
    BLOCK_DESTROY(3),
    BLOCK_DETACH(4),
    BLOCK_OPEN(5),
    BLOCK_PLACE(6),
    BLOCK_PRESS(7),
    BLOCK_SWITCH(8),
    BLOCK_UNPRESS(9),
    BLOCK_UNSWITCH(10),
    CONTAINER_CLOSE(11),
    CONTAINER_OPEN(12),
    DISPENSE_FAIL(13),
    DRINKING_FINISH(14),
    EAT(15),
    ELYTRA_FREE_FALL(16),
    ENTITY_DAMAGED(17),
    ENTITY_KILLED(18),
    ENTITY_PLACE(19),
    EQUIP(20),
    EXPLODE(21),
    FISHING_ROD_CAST(22),
    FISHING_ROD_REEL_IN(23),
    FLAP(24),
    FLUID_PICKUP(25),
    FLUID_PLACE(26),
    HIT_GROUND(27),
    LIGHTNING_STRIKE(29),
    MINECART_MOVING(30),
    MOB_INTERACT(28),
    PISTON_CONTRACT(31),
    PISTON_EXTEND(32),
    PRIME_FUSE(33),
    PROJECTILE_LAND(34),
    PROJECTILE_SHOOT(35),
    RAVAGER_ROAR(36),
    RING_BELL(37),
    SHEAR(38),
    SHULKER_CLOSE(39),
    SHULKER_OPEN(40),
    SPLASH(41),
    STEP(42),
    SWIM(43),
    WOLF_SHAKING(44);

    private final int id;

    EventTypes(int id)
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

    public static EventTypes getByIdentifier(String identifier)
    {
        return Arrays.stream(values()).filter(mat -> mat.getIdentifier().equalsIgnoreCase(identifier)).findFirst().orElse(null);
    }

    public static EventTypes getById(int id)
    {
        return Arrays.stream(values()).filter(mat -> mat.getId() == id).findFirst().orElse(null);
    }

}
