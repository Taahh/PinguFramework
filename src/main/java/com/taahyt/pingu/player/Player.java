package com.taahyt.pingu.player;

import com.taahyt.pingu.entity.Entity;
import com.taahyt.pingu.entity.EntityTypes;
import com.taahyt.pingu.equipment.MainHand;
import com.taahyt.pingu.player.profile.SkinProperty;
import com.taahyt.pingu.server.World;
import com.taahyt.pingu.server.gamemode.GameMode;
import com.taahyt.pingu.server.math.Location;
import com.taahyt.pingu.util.chat.ChatMode;
import io.netty.channel.ChannelHandlerContext;
import lombok.Getter;
import lombok.Setter;

import java.net.InetSocketAddress;
import java.util.UUID;

@Getter
@Setter
public class Player extends Entity
{
    private final UUID uuid;
    private final String name;
    private final ChannelHandlerContext connection;
    private final InetSocketAddress address;

    private String locale;
    private byte viewDistance;
    private ChatMode chatMode;
    private boolean chatColors;
    private short skinParts;
    private MainHand hand;
    private boolean textFiltering;
    private boolean serverListings;

    private World currentWorld;
    private SkinProperty skinProperty;

    private GameMode gameMode;

    private Location location;
    private boolean onGround;

    public Player(UUID uuid, String name, ChannelHandlerContext connection, InetSocketAddress address)
    {
        super(EntityTypes.PLAYER);
        this.uuid = uuid;
        this.name = name;
        this.connection = connection;
        this.address = address;
    }


}
