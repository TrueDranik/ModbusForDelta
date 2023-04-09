package bstu.graduate.modbus.service.callbackquery.impl;

import bstu.graduate.modbus.common.enums.CallbackEnum;
import bstu.graduate.modbus.service.callbackquery.Callback;
import bstu.graduate.modbus.utils.GenerateInlineKeyboardMarkup;
import bstu.graduate.modbus.utils.MessageBuilder;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.ArrayList;
import java.util.List;

@Service
public class CallbackMainMenuImpl implements Callback {

    @Override
    public List<BotApiMethod<?>> apiMethodProcessor(String callbackQueryData, Message message) {
        int messageId = message.getMessageId();
        long chatId = message.getChatId();

        List<BotApiMethod<?>> actions = new ArrayList<>();
        actions.add(generateMainMenu(messageId, chatId));

        return actions;
    }

    @Override
    public CallbackEnum getActionCallback() {
        return CallbackEnum.START_MENU;
    }

    private EditMessageText generateMainMenu(int messageId, long chatId) {
        String text = "Меню управления 'ПЧ Дельта'";

        return MessageBuilder.editMessageText(messageId, chatId, text, mainMenuKeyboard());
    }

    private InlineKeyboardMarkup mainMenuKeyboard() {
        int countButtons = 2;
        String[] textButtons = {"Команды управления", "Чтение статуса"};
        CallbackEnum[] callbacks = {CallbackEnum.CONTROL_COMMAND, CallbackEnum.READING_STATUS_COMMAND};

        return GenerateInlineKeyboardMarkup.withoutRow(countButtons, textButtons, callbacks);
    }
}
