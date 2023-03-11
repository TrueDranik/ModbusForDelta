package bstu.graduate.modbus.service.callbackquery.impl.coursework;

import bstu.graduate.modbus.common.enums.CallbackEnum;
import bstu.graduate.modbus.service.callbackquery.Callback;
import bstu.graduate.modbus.utils.GenerateInlineKeyboardMarkup;
import bstu.graduate.modbus.utils.MessageBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CallbackCourseworkDeltaMenuImpl implements Callback {
    @Override
    public List<BotApiMethod<?>> apiMethodProcessor(Message message) {
        List<BotApiMethod<?>> actions = new ArrayList<>();
        actions.add(sendCourseworkMenu(message));

        return actions;
    }

    @Override
    public CallbackEnum getActionCallback() {
        return CallbackEnum.COURSEWORK_MENU;
    }

    private EditMessageText sendCourseworkMenu(Message message) {
        Integer messageId = message.getMessageId();
        Long chatId = message.getChatId();
        String text = "Меню управления 'ПЧ Дельта' для курсовой работы";

        return MessageBuilder.editMessageText(messageId, chatId, text, deltaMenuKeyboard());
    }

    private InlineKeyboardMarkup deltaMenuKeyboard() {
        int countButtons = 1;
        String[] textButtons = {"Запустить модель"};
        CallbackEnum[] callbacks = {CallbackEnum.START_COURSEWORK};

        return GenerateInlineKeyboardMarkup.withoutRow(countButtons, textButtons, callbacks);
    }
}
