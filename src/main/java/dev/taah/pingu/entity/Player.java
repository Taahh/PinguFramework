package dev.taah.pingu.entity;

import dev.taah.pingu.server.GameMode;
import dev.taah.pingu.util.Profile;
import io.netty.util.AttributeKey;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class Player extends Entity
{
    public static final AttributeKey<Player> PLAYER_ATTRIBUTE_KEY = AttributeKey.newInstance("player");
    private Profile profile;
    private GameMode gameMode = GameMode.UNDEFINED;

    public Player(UUID uuid, int id)
    {
        super(uuid, id);
    }
}
