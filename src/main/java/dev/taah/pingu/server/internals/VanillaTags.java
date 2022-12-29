package dev.taah.pingu.server.internals;

public enum VanillaTags
{

    FLUIDS(Identifier.of("minecraft:fluid")), ITEMS(Identifier.of("minecraft:item")), BLOCKS(Identifier.of("minecraft:block")),
    GAME_EVENTS(Identifier.of("minecraft:game_event")), ENTITY_TYPES(Identifier.of("minecraft:entity_type"));

    private final Identifier identifier;

    VanillaTags(Identifier identifier)
    {
        this.identifier = identifier;
    }

    public Identifier getIdentifier()
    {
        return identifier;
    }
}
