package dev.taah.pingu.util;

import com.google.common.collect.Lists;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Data
public class Profile
{
    private final UUID uuid;
    private final String username;
    private final List<Property> properties = Lists.newArrayList();

}
