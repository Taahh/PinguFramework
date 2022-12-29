package dev.taah.pingu.client;

import java.util.Arrays;

public enum MainHand
{
    LEFT(0), RIGHT(1);

    private final int data;
    MainHand(int data)
    {
        this.data = data;
    }

    public int getData()
    {
        return data;
    }

    public static MainHand getByData(int data)
    {
        return Arrays.stream(values()).filter(chatMode -> chatMode.getData() == data).findFirst().orElse(null);
    }
}
