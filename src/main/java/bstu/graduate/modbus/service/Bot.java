package bstu.graduate.modbus.service;

import bstu.graduate.modbus.command.BaseCommand;
import bstu.graduate.modbus.common.CallbackMap;
import bstu.graduate.modbus.common.properties.TelegramProperties;
import bstu.graduate.modbus.service.callbackquery.Callback;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

@Slf4j
@Component
public class Bot extends TelegramLongPollingBot {
    private final CallbackMap callbackMap;
    private final TelegramProperties telegramProperties;
    private final List<BaseCommand> commands;

    public Bot(CallbackMap callbackMap, TelegramProperties telegramProperties, List<BaseCommand> commands) {
        super(telegramProperties.getBotToken());
        this.callbackMap = callbackMap;
        this.telegramProperties = telegramProperties;
        this.commands = commands;
    }

    @Override
    public void onUpdateReceived(Update update) {
        boolean hasCallbackQuery = update.hasCallbackQuery();
        boolean hasMessage = update.hasMessage();
        if (hasCallbackQuery) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            executeAllCallbackQueries(callbackQuery);
        } else if (hasMessage) {
            Message message = update.getMessage();

            boolean isCommand = message.isCommand();
            if (isCommand) {
                processCommand(message);
            } else {
                Long chatId = message.getChatId();
                sendMessage(chatId);
            }
        }
    }

    // TODO: 06.03.2023 написать ExceptionHandler чтобы отлавливать и логировать ошибки
    private void executeAllCallbackQueries(CallbackQuery callbackQuery) {
        String callbackQueryData = callbackQuery.getData();

        log.info("[CALLBACK]: {}", callbackQueryData);

        String[] splitCallbackQueryData = callbackQueryData.split("/");
        Callback callback = callbackMap.getCallback(splitCallbackQueryData[0]);
        List<BotApiMethod<?>> callbackQueries = callback.getCallbackQueries(callbackQuery);

        callbackQueries.forEach(this::executeCallbackQueries);
    }

    private void processCommand(Message message) {
        Long chatId = message.getChatId();
        String messageText = message.getText();

        log.info("[COMMAND]: {}", messageText);

        BaseCommand baseCommand = getBaseCommand(message);

        executeCommand(chatId, baseCommand);
    }

    private BaseCommand getBaseCommand(Message message) {
        return commands.stream()
                .filter(it -> message.getText().equals("/" + it.getBotCommand().getCommand()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No such command!"));
    }

    private void executeCommand(Long chatId, BaseCommand baseCommand) {
        try {
            BotApiMethod<?> commandAction = baseCommand.getAction(chatId);
            execute(commandAction);
        } catch (TelegramApiException e) {
            log.error("[COMMAND]: error with execute: {}.", e.getMessage());
        }
    }

    private void sendMessage(Long chatId) {
        try {
            SendMessage sendMessageMethod = new SendMessage(String.valueOf(chatId), "Неизвестное действие.");
            execute(sendMessageMethod);
        } catch (TelegramApiException e) {
            log.error("[MESSAGE]: error with execute: {}.", e.getMessage());
        }
    }

    private void executeCallbackQueries(BotApiMethod<?> apiMethod) {
        try {
            execute(apiMethod);
            Thread.sleep(2000);
        } catch (TelegramApiException | InterruptedException e) {
            log.error("[CALLBACK]: error with execute: {}.", e.getMessage());
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public String getBotUsername() {
        return telegramProperties.getBotUsername();
    }
}
