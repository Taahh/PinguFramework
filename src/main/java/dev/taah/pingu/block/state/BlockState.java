package dev.taah.pingu.block.state;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public abstract class BlockState
{
    private final int id;

    @SerializedName("default")
    private boolean isDefault;
}
