package dev.taah.pingu.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class IOUtil
{
    public static String readUrl(String url)
    {
        try
        {
            URL u = new URL(url);
            BufferedReader reader = new BufferedReader(new InputStreamReader(u.openStream()));
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null)
            {
                builder.append(line).append("\n");
            }
            return builder.toString();
        } catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

}
