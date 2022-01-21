package com.taahyt.pingu.util;

public enum ChatColor
{
    BLACK("black"),
    DARK_BLUE("dark_blue"),
    DARK_GREEN("dark_green"),
    DARK_AQUA("dark_cyan"),
    DARK_RED("dark_red"),
    PURPLE("purple"),
    GOLD("gold"),
    GRAY("gray"),
    DARK_GRAY("dark_gray"),
    BLUE("blue"),
    GREEN("green"),
    AQUA("aqua"),
    RED("red"),
    PINK("light_purple"),
    YELLOW("yellow"),
    WHITE("white");

    private final String name;
    ChatColor(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }
}
