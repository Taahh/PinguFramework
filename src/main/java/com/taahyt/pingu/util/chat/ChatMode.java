package com.taahyt.pingu.util.chat;

import java.util.Arrays;

public enum ChatMode
{
    ENABLED(0),
    COMMANDS_ONLY(1),
    HIDDEN(2);

    private final int id;
    ChatMode(int id)
    {
        this.id = id;
    }

    public int getId()
    {
        return this.id;
    }

    public static ChatMode getById(int id)
    {
        return Arrays.stream(values()).filter(mode -> mode.getId() == id).findFirst().orElse(null);
    }

}
