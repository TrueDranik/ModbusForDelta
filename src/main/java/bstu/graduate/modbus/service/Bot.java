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
            String callbackQueryData = callbackQuery.getData();

            log.info("[CALLBACK]: {}", callbackQueryData);

            String[] splitCallbackQueryData = callbackQueryData.split("/");
            Callback callback = callbackMap.getCallback(splitCallbackQueryData[0]);
            List<BotApiMethod<?>> executeCallbackQuery = callback.getCallbackQuery(callbackQuery);

            executeCallbackQuery.forEach(apiMethod -> {
                try {
                    execute(apiMethod);
                    Thread.sleep(3000);
                } catch (TelegramApiException | InterruptedException e) {
                    log.error("callback: error with execute: " + e);
                    Thread.currentThread().interrupt();
                }
            });
        } else if (hasMessage) {
            Message message = update.getMessage();
            Long chatId = message.getChatId();

            boolean isCommand = message.isCommand();
            if (isCommand) {
                log.info("[COMMAND]: {}", message.getText());

                BaseCommand baseCommand = commands.stream()
                        .filter(it -> message.getText().equals("/" + it.getBotCommand().getCommand()))
                        .findFirst()
                        .orElseThrow(() -> new IllegalArgumentException("No such command!"));

                try {
                    execute(baseCommand.getAction(chatId));
                } catch (TelegramApiException e) {
                    log.error("command: error with execute: " + e);
                }
            } else {
                try {
                    execute(new SendMessage(String.valueOf(chatId), "Lox?"));
                } catch (TelegramApiException e) {
                    log.error("message: error with execute: " + e);
                }
            }
        }
    }

    @Override
    public String getBotUsername() {
        return telegramProperties.getBotUsername();
    }
}
