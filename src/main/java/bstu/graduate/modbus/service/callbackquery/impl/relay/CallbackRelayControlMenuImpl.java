package bstu.graduate.modbus.service.callbackquery.impl.relay;

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
public class CallbackRelayControlMenuImpl implements Callback {
    private final GenerateInlineKeyboardMarkup generateInlineKeyboardMarkup;

    @Override
    public List<BotApiMethod<?>> apiMethodProcessor(Message message) {
        List<BotApiMethod<?>> actions = new ArrayList<>();
        actions.add(getEditMessageText(message));

        return actions;
    }

    private EditMessageText getEditMessageText(Message message) {
        Integer messageId = message.getMessageId();
        Long chatId = message.getChatId();

        String text = "Управление реле";
        InlineKeyboardMarkup replyMarkup = generateInlineKeyboardMarkup.createRelayMenuInlineKeyboardMarkup();

        return MessageBuilder.editMessageText(messageId, chatId, text, replyMarkup);
    }

    @Override
    public CallbackEnum getActionCallback() {
        return CallbackEnum.RELAY_CONTROL_MENU;
    }
}
