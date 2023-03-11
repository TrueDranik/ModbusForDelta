package bstu.graduate.modbus.service.callbackquery;

import bstu.graduate.modbus.common.enums.CallbackEnum;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;

@Component
public interface Callback {
    default List<BotApiMethod<?>> getCallbackQueries(CallbackQuery callbackQuery) {
        Message message = callbackQuery.getMessage();

        return apiMethodProcessor(message);
    }

    List<BotApiMethod<?>> apiMethodProcessor(Message message);

    CallbackEnum getActionCallback();
}
