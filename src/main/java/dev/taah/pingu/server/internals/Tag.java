package dev.taah.pingu.server.internals;

import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;

@Data
public class Tag
{
    private final Identifier parentIdentifier;
    private final Identifier identifier;
    private final List<Data> entries = Lists.newArrayList();


    @lombok.Data
    public static final class Data
    {
        private final Identifier identifier;
        private final int protocolId;
    }

}
