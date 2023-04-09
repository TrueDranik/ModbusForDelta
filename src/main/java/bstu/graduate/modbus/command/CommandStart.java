package bstu.graduate.modbus.command;

import bstu.graduate.modbus.common.enums.CallbackEnum;
import bstu.graduate.modbus.utils.GenerateInlineKeyboardMarkup;
import bstu.graduate.modbus.utils.MessageBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;

@Component
@RequiredArgsConstructor
public class CommandStart implements BaseCommand {

    @Override
    public BotCommand getBotCommand() {
        return BotCommand.builder()
                .command("start")
                .description("начать/восстановить работу с ботом")
                .build();
    }

    @Override
    public BotApiMethod<?> getAction(Long chatId) {
        String text = "Меню управления 'ПЧ Дельта'";
        int countButtons = 2;

        String[] textButtons = {"Команды управления", "Чтение статуса"};
        CallbackEnum[] callbacks = {CallbackEnum.CONTROL_COMMAND, CallbackEnum.READING_STATUS_COMMAND};

        return MessageBuilder.sendMessage(chatId, text, GenerateInlineKeyboardMarkup.withoutRow(countButtons, textButtons, callbacks));
    }
}
