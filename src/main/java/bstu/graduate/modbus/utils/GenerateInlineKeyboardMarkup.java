package bstu.graduate.modbus.utils;

import bstu.graduate.modbus.common.enums.CallbackEnum;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
public class GenerateInlineKeyboardMarkup {
    /**
     * Генерирует клавиатуру без колонок по следующим параметрам:
     * @param countButtons (кол-во кнопок)
     * @param texts (Массив текста для кнопок в порядке от первой к последней)
     * @param callbacks (Массив коллбэков для кнопок в порядке от первой к последней)
     * @return InlineKeyboardMarkup
     */
    public static InlineKeyboardMarkup withoutRow(Integer countButtons, String[] texts, CallbackEnum[] callbacks) {
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>(countButtons);

        for (int i = 0; i < countButtons; i++) {
            buttons.add(List.of(InlineKeyboardButton.builder()
                    .text(texts[i])
                    .callbackData(String.valueOf(callbacks[i]))
                    .build()));
        }

        return InlineKeyboardMarkup.builder()
                .keyboard(buttons)
                .build();
    }

    public InlineKeyboardMarkup createRelayMenuInlineKeyboardMarkup() {
        List<InlineKeyboardButton> firstRow = new ArrayList<>(2);
        List<InlineKeyboardButton> lastRow = new ArrayList<>(1);

        firstRow.add(InlineKeyboardButton.builder()
                .text("Включить первое реле")
                .callbackData(CallbackEnum.TURN_ON_FIRST_RELAY.toString())
                .build());
        firstRow.add(InlineKeyboardButton.builder()
                .text("Выключить первое реле")
                .callbackData(CallbackEnum.TURN_OFF_FIRST_RELAY.toString())
                .build());

        lastRow.add(InlineKeyboardButton.builder()
                .text("Назад")
                .callbackData(CallbackEnum.START_MENU.toString())
                .build());

        return InlineKeyboardMarkup.builder()
                .keyboardRow(firstRow)
                .keyboardRow(lastRow)
                .build();
    }
}
