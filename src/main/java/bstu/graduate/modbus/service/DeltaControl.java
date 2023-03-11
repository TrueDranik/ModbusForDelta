package bstu.graduate.modbus.service;

import bstu.graduate.modbus.common.enums.CommandForDelta;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DeltaControl {
    /**
     * Запустить двигатель вперед
     */
    public void runForward() {
        PortHandler.writeBytes(CommandForDelta.RUN_FORWARD);
        log.info("[DELTA]: Двигатель запущен");
    }

    /**
     * Остановить двигатель
     */
    public void stop() {
        PortHandler.writeBytes(CommandForDelta.STOP);
        log.info("[DELTA]: Двигатель остановлен");
    }

    /**
     * Задать частоту
     */
    public void setFrequency(String frequencyValue) {
        PortHandler.writeBytes(CommandForDelta.SET_FREQUENCY, frequencyValue);
        log.info("[DELTA]: Установлена частота: {}", frequencyValue);
    }

    /**
     * Получить значение частоты
     */
    public void getFrequency() throws InterruptedException {
        PortHandler.writeBytes(CommandForDelta.GET_FREQUENCY);
        int readBytes = PortHandler.readBytes();
        log.info("[DELTA]: Частота двигателя: {}", readBytes);
    }
}
