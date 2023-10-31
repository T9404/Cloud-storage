package org.example.util;

public  class CommandUtil {

    public static String getCommandName(String command) {
        String[] parts = command.split("\\s+", 2);
        return parts[0];
    }
}
