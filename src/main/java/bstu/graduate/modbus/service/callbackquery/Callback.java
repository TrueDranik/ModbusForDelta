package bstu.graduate.modbus.service.callbackquery;

import bstu.graduate.modbus.common.enums.CallbackEnum;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.util.List;

@Component
public interface Callback {
    List<BotApiMethod<?>> getCallbackQuery(CallbackQuery callbackQuery);

    CallbackEnum getActionCallback();
}
