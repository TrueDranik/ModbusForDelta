package bstu.graduate.modbus.service.callbackquery.impl;

import bstu.graduate.modbus.common.enums.CallbackEnum;
import bstu.graduate.modbus.service.callbackquery.Callback;
import bstu.graduate.modbus.utils.GenerateInlineKeyboardMarkup;
import bstu.graduate.modbus.utils.MessageBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CallbackDeltaMenuImpl implements Callback {
    @Override
    public List<BotApiMethod<?>> apiMethodProcessor(Message message) {
        List<BotApiMethod<?>> actions = new ArrayList<>(1);
        actions.add(generateMainMenu(message));

        return actions;
    }

    @Override
    public CallbackEnum getActionCallback() {
        return CallbackEnum.DELTA_FC_CONTROL_MENU;
    }

    private EditMessageText generateMainMenu(Message message) {
        Integer messageId = message.getMessageId();
        Long chatId = message.getChatId();
        String text = "Меню управления 'ПЧ Дельта'";

        return MessageBuilder.editMessageText(messageId, chatId, text, deltaMenuKeyboard());
    }

    private InlineKeyboardMarkup deltaMenuKeyboard() {
        int countButtons = 2;
        String[] textButtons = {"Курсовая работа", "Дипломная работа"};
        CallbackEnum[] callbacks = {CallbackEnum.COURSEWORK_MENU, CallbackEnum.GRADUATEWORK_MENU};

        return GenerateInlineKeyboardMarkup.withoutRow(countButtons, textButtons, callbacks);
    }
}
