package dev.taah.pingu.server;

public enum GameMode
{
    UNDEFINED(-1),
    SURVIVAL(0),
    CREATIVE(1),
    ADVENTURE(2),
    SPECTATOR(3);
    private final int mode;

    GameMode(int mode)
    {
        this.mode = mode;
    }

    public int getNumber()
    {
        return mode;
    }
}
