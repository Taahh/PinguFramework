package com.taahyt.pingu.server;

import com.google.common.collect.Maps;
import com.taahyt.pingu.player.Player;

import java.net.InetSocketAddress;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;

public class Server
{

    private final Map<UUID, Player> onlinePlayers = Maps.newHashMap();
    private final Map<String, World> worlds = Maps.newHashMap();


    public void addPlayer(Player player)
    {
        this.onlinePlayers.put(player.getUuid(), player);
    }

    public void removePlayer(Player player)
    {
        removePlayer(player.getUuid());
    }

    public void removePlayer(UUID uuid)
    {
        this.onlinePlayers.remove(uuid);
    }

    public Player getPlayer(UUID uuid)
    {
        return this.onlinePlayers.get(uuid);
    }

    public Player getPlayer(InetSocketAddress address)
    {
        return this.onlinePlayers.values().stream().filter(player -> player.getAddress().equals(address)).findFirst().orElse(null);
    }

    public void addWorld(World world)
    {
        this.worlds.put(world.getName(), world);
    }

    public World getWorld(String world)
    {
        return this.worlds.get(world);
    }



    public Collection<Player> getOnlinePlayers()
    {
        return this.onlinePlayers.values();
    }

}
