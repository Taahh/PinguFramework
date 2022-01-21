package com.taahyt.pingu.messages;

import com.taahyt.pingu.messages.handshaking.*;
import com.taahyt.pingu.messages.login.ServerboundLoginStartMessage;
import com.taahyt.pingu.messages.play.ServerboundClientSettingsMessage;
import com.taahyt.pingu.messages.play.ServerboundClientStatusMessage;
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

    CLIENT_STATUS(0x04, new ServerboundClientStatusMessage(), Status.PLAY),
    CLIENT_SETTINGS(0x05, new ServerboundClientSettingsMessage(), Status.PLAY);

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
