package bstu.graduate.modbus.service.callbackquery.impl.command.control;

import bstu.graduate.modbus.common.enums.CallbackEnum;
import bstu.graduate.modbus.service.callbackquery.Callback;
import bstu.graduate.modbus.utils.MessageBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CallbackControlCommandImpl implements Callback {

    @Override
    public List<BotApiMethod<?>> apiMethodProcessor(String callbackQueryData, Message message) {
        int messageId = message.getMessageId();
        long chatId = message.getChatId();

        List<BotApiMethod<?>> actions = new ArrayList<>(1);
        actions.add(generateMainMenu(messageId, chatId));

        return actions;
    }

    @Override
    public CallbackEnum getActionCallback() {
        return CallbackEnum.CONTROL_COMMAND;
    }

    private EditMessageText generateMainMenu(int messageId, long chatId) {
        String text = "Команды управления";

        return MessageBuilder.editMessageText(messageId, chatId, text, ControlCommandMenuInlineKeyboard.getMenu());
    }
}
