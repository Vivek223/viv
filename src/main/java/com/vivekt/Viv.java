package com.vivekt;

import com.vivekt.command.*;

import java.util.Arrays;

public class Viv {

    public static void main(String[] args) {

        if (args.length == 0) {
            System.out.println("Usage: java Main <command>");
            return;
        }

        String commandName = args[0].toLowerCase();
        Command command;

        switch (commandName) {
            case "init":
                command = new InitCommand();
                break;
            case "list":
                command = new ListCommand();
                break;
            default:
                System.out.println("Unknown command: " + commandName);
                return;
        }

        command.execute(Arrays.copyOfRange(args, 1, args.length));
    }
}
