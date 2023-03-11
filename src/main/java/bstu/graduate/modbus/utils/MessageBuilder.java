package bstu.graduate.modbus.utils;

import lombok.experimental.UtilityClass;
import org.springframework.lang.Nullable;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

@UtilityClass
public class MessageBuilder {
    public static EditMessageText editMessageText(Integer messageId, Long chatId, String text, @Nullable InlineKeyboardMarkup replyMarkup) {
        return EditMessageText.builder()
                .messageId(messageId)
                .chatId(chatId)
                .text(text)
                .parseMode(ParseMode.MARKDOWN)
                .replyMarkup(replyMarkup)
                .build();
    }

    public static EditMessageText editMessageText(Integer messageId, Long chatId, String text) {
        return editMessageText(messageId, chatId, text, null);
    }

    public static SendMessage sendMessage(Long chatId, String text, @Nullable ReplyKeyboard replyMarkup) {
        return SendMessage.builder()
                .chatId(chatId)
                .text(text)
                .parseMode(ParseMode.MARKDOWN)
                .replyMarkup(replyMarkup)
                .build();
    }

    public static SendMessage sendMessage(Long chatId, String text){
        return sendMessage(chatId, text, null);
    }
}
