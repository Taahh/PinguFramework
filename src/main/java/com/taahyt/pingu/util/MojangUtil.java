package com.taahyt.pingu.util;

import com.google.common.collect.Maps;
import lombok.SneakyThrows;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * Easily access mojang utilities through web requests
 * @author Taah
 */

public class MojangUtil
{
    /**
     * Skin Cache
     */
    private static final Map<String, String[]> SKIN_CACHE = Maps.newHashMap();

    /**
     * Gets UUID of a player
     * @param name Player username
     * @return UUID in String format
     */
    public static String getUUID(String name)
    {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet get = new HttpGet("https://api.mojang.com/users/profiles/minecraft/" + name);
        try {
            HttpResponse response = client.execute(get);
            if (response.getStatusLine().getStatusCode() == 200) {
                String json = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
                JSONObject object = new JSONObject(json);
                client.close();
                return object.getString("id");
            } else {
                return "not found";
            }
        } catch (IOException e) {
            //e.printStackTrace();
        }
        return "not found";
    }

    /**
     * Gets the skin value and signature of a player using Mineskin API
     * @param username Player username
     * @return A completable future containing an array of data (0: value, 1: signature)
     */
    @SneakyThrows
    public static CompletableFuture<String[]> getSkinData(String username)
    {
        return CompletableFuture.supplyAsync(() -> {
            CloseableHttpClient client = HttpClients.createDefault();
            HttpGet get = new HttpGet("https://api.mineskin.org/generate/user/" + getUUID(username));
            try {
                HttpResponse response = client.execute(get);
                String json = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
                JSONObject object = new JSONObject(json);
                String value = (String) object.getJSONObject("data").getJSONObject("texture").get("value");
                String signature = (String) object.getJSONObject("data").getJSONObject("texture").get("signature");

                client.close();
                String[] data = SKIN_CACHE.put(username, new String[]{value, signature});
                new Timer().schedule(new TimerTask()
                {
                    @Override
                    public void run()
                    {
                        SKIN_CACHE.remove(username);
                    }
                }, 5000);
                return new String[]{value, signature};
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        });
    }

}
