package dev.taah.pingu.client;

import java.util.Arrays;

public enum ChatMode
{
    ENABLED(0), COMMANDS_ONLY(1), HIDDEN(2);

    private final int data;
    ChatMode(int data)
    {
        this.data = data;
    }

    public int getData()
    {
        return data;
    }

    public static ChatMode getByData(int data)
    {
        return Arrays.stream(values()).filter(chatMode -> chatMode.getData() == data).findFirst().orElse(null);
    }
}
