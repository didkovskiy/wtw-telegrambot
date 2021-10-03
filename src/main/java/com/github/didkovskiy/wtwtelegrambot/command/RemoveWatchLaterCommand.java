package com.github.didkovskiy.wtwtelegrambot.command;

import com.github.didkovskiy.wtwtelegrambot.repository.entity.TelegramUser;
import com.github.didkovskiy.wtwtelegrambot.repository.entity.WatchLater;
import com.github.didkovskiy.wtwtelegrambot.service.SendBotMessageService;
import com.github.didkovskiy.wtwtelegrambot.service.TelegramUserService;
import com.github.didkovskiy.wtwtelegrambot.service.WatchLaterService;
import org.telegram.telegrambots.meta.api.objects.Update;

import javax.ws.rs.NotFoundException;

import java.util.List;
import java.util.Optional;

import static com.github.didkovskiy.wtwtelegrambot.command.CommandName.REMOVE_WATCH_LATER;
import static com.github.didkovskiy.wtwtelegrambot.command.CommandUtils.getChatId;
import static com.github.didkovskiy.wtwtelegrambot.command.CommandUtils.getMessage;
import static org.apache.commons.lang3.StringUtils.SPACE;
import static org.apache.commons.lang3.StringUtils.isNumeric;

/**
 * Remove Watch Later {@link Command}.
 */
public class RemoveWatchLaterCommand implements Command {

    private final SendBotMessageService sendBotMessageService;
    private final TelegramUserService telegramUserService;
    private final WatchLaterService watchLaterService;

    public RemoveWatchLaterCommand(SendBotMessageService sendBotMessageService, TelegramUserService telegramUserService, WatchLaterService watchLaterService) {
        this.sendBotMessageService = sendBotMessageService;
        this.telegramUserService = telegramUserService;
        this.watchLaterService = watchLaterService;
    }

    @Override
    public void execute(Update update) {
        if (getMessage(update).equalsIgnoreCase(REMOVE_WATCH_LATER.getCommandName())) {
            sendWatchLaterList(getChatId(update));
            return;
        }
        String watchLaterMovieNumber = getMessage(update).split(SPACE)[1];
        String chatId = getChatId(update);
        if (isNumeric(watchLaterMovieNumber) && Integer.parseInt(watchLaterMovieNumber) != 0) {
            TelegramUser telegramUser = telegramUserService.findByChatId(chatId).orElseThrow(NotFoundException::new);
            if (telegramUser.getWatchLaterList().size() < Integer.parseInt(watchLaterMovieNumber)) {
                sendWatchLaterNotFound(chatId, watchLaterMovieNumber);
                return;
            }
            String watchLaterId = telegramUser.getWatchLaterList().get(Integer.parseInt(watchLaterMovieNumber) - 1).getId();
            Optional<WatchLater> watchLaterOptional = watchLaterService.findById(watchLaterId);
            if (watchLaterOptional.isPresent()) {
                WatchLater watchLater = watchLaterOptional.get();
                watchLater.getUsers().remove(telegramUser);
                watchLaterService.save(watchLater);
                sendBotMessageService.sendMessage(chatId, String.format("Movie <b>%s</b> has been removed from WatchLater list.", watchLater.getTitle()));
            } else {
                sendWatchLaterNotFound(chatId, watchLaterMovieNumber);
            }
        } else {
            sendBotMessageService.sendMessage(chatId, "Enter a positive integer > 0.");
        }
    }

    private void sendWatchLaterList(String chatId) {
        List<WatchLater> watchLaterList = telegramUserService.findByChatId(chatId).orElseThrow(NotFoundException::new)
                .getWatchLaterList();
        if (watchLaterList.isEmpty()) {
            sendBotMessageService.sendMessage(chatId, "Your WatchLater list is empty.");
        } else {
            StringBuilder sb = new StringBuilder("After /remove please type a number of a movie in the list: \n\n");
            for (int i = 0; i < watchLaterList.size(); i++) {
                sb.append(String.format((i + 1) + "\n" +
                                "<b>Title:</b> %s\n" + "<b>Description:</b> %s\n\n",
                        watchLaterList.get(i).getTitle(), watchLaterList.get(i).getDescription()));
            }
            sendBotMessageService.sendMessage(chatId, sb.toString());
        }
    }

    private void sendWatchLaterNotFound(String chatId, String watchLaterMovieNumber) {
        sendBotMessageService.sendMessage(chatId, "There is no movie with number " + watchLaterMovieNumber + " in the list");
    }
}
