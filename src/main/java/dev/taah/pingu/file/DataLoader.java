package dev.taah.pingu.file;

import lombok.Getter;
import net.kyori.adventure.nbt.BinaryTagIO;
import net.kyori.adventure.nbt.CompoundBinaryTag;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class DataLoader
{

    private static final String VERSION = "1.19.2";
    private static final File DATA_FOLDER = new File("data");

    @Getter
    private final CompoundBinaryTag registryCodec;

    static {
        if (!DATA_FOLDER.exists()) {
            DATA_FOLDER.mkdir();
        }
    }

    public DataLoader()
    {
        try
        {
            this.registryCodec = BinaryTagIO.unlimitedReader().read(new URL("https://minecraft.taah.dev/registryCodec-" + VERSION + ".nbt").openStream(), BinaryTagIO.Compression.GZIP);
        } catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }


}
