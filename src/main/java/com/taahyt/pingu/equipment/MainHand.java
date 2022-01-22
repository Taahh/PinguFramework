package com.taahyt.pingu.equipment;

import java.util.Arrays;

public enum MainHand
{
    LEFT(0),
    RIGHT(1);

    private final int id;
    MainHand(int id)
    {
        this.id = id;
    }

    public int getId()
    {
        return this.id;
    }

    public static MainHand getById(int id)
    {
        return Arrays.stream(values()).filter(hand -> hand.getId() == id).findFirst().orElse(null);
    }
}
