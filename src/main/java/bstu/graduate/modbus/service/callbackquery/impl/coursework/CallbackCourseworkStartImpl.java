package bstu.graduate.modbus.service.callbackquery.impl.coursework;

import bstu.graduate.modbus.common.enums.CallbackEnum;
import bstu.graduate.modbus.service.callbackquery.Callback;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CallbackCourseworkStartImpl implements Callback {
    @Override
    public List<BotApiMethod<?>> apiMethodProcessor(Message message) {
        List<BotApiMethod<?>> actions = new ArrayList<>();

        return actions;
    }

    @Override
    public CallbackEnum getActionCallback() {
        return null;
    }
}
