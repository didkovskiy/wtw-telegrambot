package com.github.didkovskiy.wtwtelegrambot.command;

/**
 * Enumeration for {@link Command}'s.
 */
public enum CommandName {

    START("/start"),
    STOP("/stop"),
    HELP("/help"),
    NO("/nocommand"),
    STAT("/stat"),
    WATCH_LATER("/watchlater"),
    REMOVE_WATCH_LATER("/remove"),
    RANDOM("/random"),
    TRAILER("/trailer"),
    CLEAR_WATCH_LATER("/clear");

    private final String commandName;

    CommandName(String commandName) {
        this.commandName = commandName;
    }

    public String getCommandName() {
        return commandName;
    }

}