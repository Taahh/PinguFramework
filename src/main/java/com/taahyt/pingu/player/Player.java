package com.taahyt.pingu.player;

import lombok.Data;

import java.net.InetSocketAddress;
import java.util.UUID;

@Data
public class Player
{
    private final UUID uuid;
    private final String name;
    private final InetSocketAddress address;
}
