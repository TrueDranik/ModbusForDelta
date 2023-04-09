package bstu.graduate.modbus.service.callbackquery.impl.command.control;

import bstu.graduate.modbus.common.enums.CallbackEnum;
import bstu.graduate.modbus.service.DeltaControl;
import bstu.graduate.modbus.service.callbackquery.Callback;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CallbackRunReversImpl implements Callback {

    private final DeltaControl deltaControl;

    @Override
    public List<BotApiMethod<?>> apiMethodProcessor(String callbackQueryData, Message message) {
        int messageId = message.getMessageId();
        long chatId = message.getChatId();

        List<BotApiMethod<?>> actions = new ArrayList<>();
        actions.add(runReverse(messageId, chatId));
        actions.add(sendMenu(chatId));

        return actions;
    }

    @Override
    public CallbackEnum getActionCallback() {
        return CallbackEnum.RUN_REVERS;
    }

    private EditMessageText runReverse(int messageId, long chatId) {
        deltaControl.runRevers();

        return EditMessageText.builder()
                .messageId(messageId)
                .chatId(chatId)
                .text("Двигатель запущен назад.")
                .parseMode(ParseMode.MARKDOWN)
                .build();
    }

    private SendMessage sendMenu(long chatId) {
        InlineKeyboardMarkup menu = ControlCommandMenuInlineKeyboard.getMenu();

        return SendMessage.builder()
                .chatId(chatId)
                .text("Команды управления")
                .parseMode(ParseMode.MARKDOWN)
                .replyMarkup(menu)
                .build();
    }
}
