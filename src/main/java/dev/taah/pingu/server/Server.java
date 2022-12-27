package dev.taah.pingu.server;

import com.google.common.collect.Maps;
import dev.taah.pingu.entity.Player;
import dev.taah.pingu.file.DataLoader;
import dev.taah.pingu.world.World;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;

@Setter
@Getter
public class Server
{
    @Getter(AccessLevel.NONE)
    private final Map<UUID, Player> players = Maps.newHashMap();
    @Getter(AccessLevel.NONE)
    private final Map<String, World> worlds = Maps.newHashMap();
    private final DataLoader loader = new DataLoader();
    private final ServerSettings serverSettings = new ServerSettings();
    private int lastEntityId;

    public int getNextEntityId() {
        return lastEntityId++;
    }

    public Collection<Player> getPlayers() {
        return players.values();
    }

    public Player getPlayer(UUID uuid) {
        if (!players.containsKey(uuid)) {
            Player player = new Player(uuid, getNextEntityId());
            this.players.put(uuid, player);
            return player;
        }
        return players.get(uuid);
    }




}
