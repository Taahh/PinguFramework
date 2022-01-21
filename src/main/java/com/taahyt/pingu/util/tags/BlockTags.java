package com.taahyt.pingu.util.tags;

import com.google.common.collect.Lists;

import java.util.List;

public class BlockTags
{
    public static final List<Tag> BLOCK_TAGS = Lists.newArrayList();

    public static final Tag MINEABLE_AXE = bind("mineable/axe");
    public static final Tag MINEABLE_HOE = bind("mineable/hoe");
    public static final Tag MINEABLE_PICKAXE = bind("mineable/pickaxe");
    public static final Tag MINEABLE_SHOVEL = bind("mineable/shovel");

    public static final Tag ACACIA_LOGS = bind("acacia_logs");
    public static final Tag ANIMALS_SPAWNABLE_ON = bind("animals_spawnable_on");
    public static final Tag ANVIL = bind("anvil");

    public static final Tag AXOLOTLS_SPAWNABLE_ON = bind("axolotls_spawnable_on");
    public static final Tag AZALEA_GROWS_ON = bind("azalea_grows_on");
    public static final Tag AZALEA_ROOT_REPLACEABLE = bind("azalea_root_replaceable");
    public static final Tag BAMBOO_PLANTABLE_ON = bind("bamboo_plantable_on");

    public static final Tag BANNERS = bind("banners");
    public static final Tag BASE_STONER_NETHER = bind("base_stone_nether");
    public static final Tag BASE_STONE_OVERWORLD = bind("base_stone_overworld");
    public static final Tag BEACON_BASE_BLOCKS = bind("beacon_base_blocks");

    private static Tag bind(String name)
    {
        Tag tag = new Tag(name);
        BLOCK_TAGS.add(tag);
        return tag;
    }


}
