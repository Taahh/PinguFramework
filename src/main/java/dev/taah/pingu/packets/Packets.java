package dev.taah.pingu.packets;

import dev.taah.pingu.client.Status;
import dev.taah.pingu.packets.handshake.ServerboundHandshakePacket;
import dev.taah.pingu.packets.login.ServerboundLoginPacket;
import dev.taah.pingu.packets.play.ServerboundClientInfoPacket;
import dev.taah.pingu.packets.play.ServerboundConfirmTeleportPacket;
import dev.taah.pingu.packets.status.ServerboundPingPacket;
import dev.taah.pingu.packets.status.ServerboundStatusPacket;
import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

public enum Packets
{
    HANDSHAKE(0x00, new ServerboundHandshakePacket(), Status.HANDSHAKE),
    STATUS_REQUEST(0x00, new ServerboundStatusPacket(), Status.STATUS),
    PING_REQUEST(0x01, new ServerboundPingPacket(), Status.STATUS),
    LOGIN_START(0x00, new ServerboundLoginPacket(), Status.LOGIN),

    CONFIRM_TELEPORT(0x00, new ServerboundConfirmTeleportPacket(), Status.PLAY),
    CLIENT_INFORMATION(0x07, new ServerboundClientInfoPacket(), Status.PLAY);

    @Getter
    private final int packetId;
    @Getter
    private final AbstractPacket message;
    @Getter
    private final Status status;

    Packets(int packetId, AbstractPacket message, Status status)
    {
        this.packetId = packetId;
        this.message = message;
        this.status = status;
    }

    public static AbstractPacket getById(Status status, int packetId)
    {
        Optional<AbstractPacket> message = Arrays.stream(values()).filter(packet -> packet.getPacketId() == packetId && packet.getStatus() == status).map(Packets::getMessage).findFirst();
        return message.orElse(null);
    }
}
