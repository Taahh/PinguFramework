package dev.taah.pingu.server.internals;

import lombok.Data;

@Data
public class Identifier
{
    private final String namespace;
    private final String path;

    public String toString()
    {
        return namespace + ":" + path;
    }

    public boolean equals(Identifier other)
    {
        return other.getNamespace().replace("#", "").equals(this.getNamespace().replace("#", "")) && other.getPath().replace("#", "").equals(this.getPath().replace("#", ""));
    }

    public static Identifier of(String identifier)
    {
        String[] args = identifier.split(":");
        return new Identifier(args[0], args[1]);
    }
}
