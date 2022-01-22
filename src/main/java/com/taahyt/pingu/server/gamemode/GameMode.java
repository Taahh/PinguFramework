package com.taahyt.pingu.server.gamemode;

import java.util.Arrays;

public enum GameMode
{
    SURVIVAL(0),
    CREATIVE(1),
    ADVENTURE(2),
    SPECTATOR(3);

    private final int id;

    GameMode(int id)
    {
        this.id = id;
    }

    public int getId()
    {
        return this.id;
    }


    public static GameMode getById(int id)
    {
        return Arrays.stream(values()).filter(gameMode -> gameMode.getId() == id).findFirst().orElse(null);
    }

}
