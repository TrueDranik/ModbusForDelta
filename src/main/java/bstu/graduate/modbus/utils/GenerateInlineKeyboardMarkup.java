package bstu.graduate.modbus.utils;

import bstu.graduate.modbus.common.enums.CallbackEnum;
import lombok.experimental.UtilityClass;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class GenerateInlineKeyboardMarkup {

    /**
     * Генерирует клавиатуру без колонок по следующим параметрам:
     *
     * @param countButtons (кол-во кнопок)
     * @param texts        (Массив текста для кнопок в порядке от первой к последней)
     * @param callbacks    (Массив коллбэков для кнопок в порядке от первой к последней)
     */
    public static InlineKeyboardMarkup withoutRow(int countButtons, String[] texts, CallbackEnum[] callbacks) {
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
}
