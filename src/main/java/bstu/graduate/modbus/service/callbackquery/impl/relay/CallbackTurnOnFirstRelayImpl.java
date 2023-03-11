package bstu.graduate.modbus.service.callbackquery.impl.relay;

import bstu.graduate.modbus.common.enums.CallbackEnum;
import bstu.graduate.modbus.common.enums.CommandForDelta;
import bstu.graduate.modbus.service.PortHandler;
import bstu.graduate.modbus.service.callbackquery.Callback;
import bstu.graduate.modbus.utils.GenerateInlineKeyboardMarkup;
import bstu.graduate.modbus.utils.MessageBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CallbackTurnOnFirstRelayImpl implements Callback {
    private final GenerateInlineKeyboardMarkup generateInlineKeyboardMarkup;

    @Override
    public List<BotApiMethod<?>> apiMethodProcessor(Message message) {
        List<BotApiMethod<?>> actions = new ArrayList<>();
        Integer messageId = message.getMessageId();
        Long chatId = message.getChatId();

        actions.add(turnOnFirstRelay(messageId, chatId));
        try {
            actions.add(readBytes(messageId, chatId));
        } catch (InterruptedException e) {
            actions.add(sendReadErrorMessage(chatId, e.getMessage()));
            log.error("Ошибка чтения массива байтов: {}", e.getMessage());
            Thread.currentThread().interrupt();
        }
        actions.add(sendRelayMenu(messageId, chatId));

        return actions;
    }

    private EditMessageText turnOnFirstRelay(Integer messageId, Long chatId) {
        String text = "Включение первого реле.";
        PortHandler.writeBytes(CommandForDelta.TURN_ON);

        return MessageBuilder.editMessageText(messageId, chatId, text);
    }

    private EditMessageText readBytes(Integer messageId, Long chatId) throws InterruptedException {
        int numRead = PortHandler.readBytes();
        String text = "Прочитано " + numRead + " байт.";

        return MessageBuilder.editMessageText(messageId, chatId, text);
    }

    private static SendMessage sendReadErrorMessage(Long chatId, String errorMessage) {
        String text = "Ошибка чтения массива байтов: %s".formatted(errorMessage);

        return MessageBuilder.sendMessage(chatId, text);
    }

    private EditMessageText sendRelayMenu(Integer messageId, Long chatId) {
        String text = "Меню управления реле";
        InlineKeyboardMarkup replyMarkup = generateInlineKeyboardMarkup.createRelayMenuInlineKeyboardMarkup();

        return MessageBuilder.editMessageText(messageId, chatId, text, replyMarkup);
    }

    @Override
    public CallbackEnum getActionCallback() {
        return CallbackEnum.TURN_ON_FIRST_RELAY;
    }
}
