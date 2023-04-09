package bstu.graduate.modbus.service.callbackquery.impl.command.control;

import bstu.graduate.modbus.common.enums.CallbackEnum;
import bstu.graduate.modbus.utils.GenerateInlineKeyboardMarkup;
import lombok.experimental.UtilityClass;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

@UtilityClass
public class ControlCommandMenuInlineKeyboard {

    InlineKeyboardMarkup getMenu() {
        int countButtons = 3;
        String[] textButtons = {"Запустить двигатель вперед", "Запустить двигатель назад", "Остановить двигатель"};
        CallbackEnum[] callbacks = {CallbackEnum.RUN_FORWARD, CallbackEnum.RUN_REVERS, CallbackEnum.STOP};

        return GenerateInlineKeyboardMarkup.withoutRow(countButtons, textButtons, callbacks);
    }
}
