package com.taahyt.pingu.messages;

import com.taahyt.pingu.messages.handshaking.*;
import com.taahyt.pingu.messages.login.ServerboundLoginStartMessage;
import com.taahyt.pingu.messages.play.*;
import com.taahyt.pingu.messages.status.ClientboundPingMessage;
import com.taahyt.pingu.messages.status.ClientboundRequestMessage;
import com.taahyt.pingu.messages.status.ServerboundPingMessage;
import com.taahyt.pingu.messages.status.ServerboundRequestMessage;
import com.taahyt.pingu.util.Status;
import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

public enum Messages
{
    HANDSHAKE(0x00, new ServerboundHandshakeMessage(), Status.HANDSHAKING),

    REQUEST(0x00, new ServerboundRequestMessage(), Status.STATUS),
    RESPONSE(0x00, new ClientboundRequestMessage(), Status.STATUS),

    PING(0x01, new ServerboundPingMessage(), Status.STATUS),
    PONG(0x01, new ClientboundPingMessage(), Status.STATUS),

    LOGIN_START(0x00, new ServerboundLoginStartMessage(), Status.LOGIN),

    TELEPORT_CONFIRM(0x00, new ServerboundTeleportConfirmMessage(), Status.PLAY),
    CHAT_MESSAGE(0x03, new ServerboundChatMessage(), Status.PLAY),
    CLIENT_STATUS(0x04, new ServerboundClientStatusMessage(), Status.PLAY),
    CLIENT_SETTINGS(0x05, new ServerboundClientSettingsMessage(), Status.PLAY),
    PLAYER_POSITION(0x11, new ServerboundPlayerPositionMessage(), Status.PLAY),
    //PLAYER_POS_AND_ROT(0x12, new ServerboundPlayerPosAndRotMessage(), Status.PLAY);
    PLAYER_ROTATION(0x13, new ServerboundPlayerRotationMessage(), Status.PLAY),
    PLAYER_MOVEMENT(0x14, new ServerboundPlayerMovementMessage(), Status.PLAY);

    @Getter
    private final int packetId;
    @Getter
    private final AbstractMessage message;
    @Getter
    private final Status status;
    Messages(int packetId, AbstractMessage message, Status status)
    {
        this.packetId = packetId;
        this.message = message;
        this.status = status;
    }

    public static AbstractMessage getById(Status status, int packetId)
    {
        Optional<AbstractMessage> message = Arrays.stream(values()).filter(packet -> packet.getPacketId() == packetId && packet.getStatus() == status).map(Messages::getMessage).findFirst();
        return message.orElse(null);
    }
}
