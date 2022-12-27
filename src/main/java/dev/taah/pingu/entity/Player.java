package dev.taah.pingu.entity;

import dev.taah.pingu.server.GameMode;
import dev.taah.pingu.util.Profile;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class Player extends Entity
{
    private Profile profile;
    private GameMode gameMode = GameMode.UNDEFINED;

    public Player(UUID uuid, int id)
    {
        super(uuid, id);
    }
}
