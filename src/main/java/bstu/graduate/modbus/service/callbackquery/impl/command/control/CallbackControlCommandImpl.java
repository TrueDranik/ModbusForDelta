package bstu.graduate.modbus.service.callbackquery.impl.command.control;

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
public class CallbackControlCommandImpl implements Callback {

    @Override
    public List<BotApiMethod<?>> apiMethodProcessor(Message message) {
        List<BotApiMethod<?>> actions = new ArrayList<>(1);
        actions.add(generateMainMenu(message));

        return actions;
    }

    @Override
    public CallbackEnum getActionCallback() {
        return CallbackEnum.CONTROL_COMMAND;
    }

    private EditMessageText generateMainMenu(Message message) {
        Integer messageId = message.getMessageId();
        Long chatId = message.getChatId();
        String text = "Команды управления";

        return MessageBuilder.editMessageText(messageId, chatId, text, deltaMenuKeyboard());
    }

    private InlineKeyboardMarkup deltaMenuKeyboard() {
        int countButtons = 3;
        String[] textButtons = {"Запустить двигатель вперед", "Запустить двигатель назад", "Остановить двигатель"};
        CallbackEnum[] callbacks = {CallbackEnum.RUN_FORWARD, CallbackEnum.RUN_REVERS, CallbackEnum.STOP};

        return GenerateInlineKeyboardMarkup.withoutRow(countButtons, textButtons, callbacks);
    }
}
