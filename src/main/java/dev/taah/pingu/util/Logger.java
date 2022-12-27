package dev.taah.pingu.util;

public class Logger
{

    public static void log(String message, Object... args) {
        for (int i = 0; i < args.length; i++) {
            message = message.replace("{" + i + "}", String.valueOf(args[i]));
        }

        System.out.println("INFO: " + message);
    }

}
