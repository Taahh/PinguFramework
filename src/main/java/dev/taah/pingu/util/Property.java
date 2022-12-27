package dev.taah.pingu.util;

import dev.taah.pingu.handler.PacketBuffer;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class Property
{
    private final String name, value;
    private boolean signed;
    private String signature;
}
