package com.taahyt.pingu.util.chat;

import com.google.gson.Gson;
import lombok.Data;

@Data
public class ChatComponent
{

    public String text;
    public boolean bold, italic, underlined, strikethrough, obfuscated;
    public String color;

    public String toString()
    {
        return new Gson().toJson(this);
    }

}
