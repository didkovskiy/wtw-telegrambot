package com.github.didkovskiy.wtwtelegrambot.command.annotation;

import com.github.didkovskiy.wtwtelegrambot.command.Command;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Mark if {@link Command} can be viewed only by admins.
 */
@Retention(RUNTIME)
public @interface AdminCommand {
}
