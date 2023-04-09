package bstu.graduate.modbus.common.enums;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum CommandForDelta {

    /**
     * Запуск двигателя вперед.
     * Адрес в сети - 01; Функция - 06; Адрес регистра - 20 00; Команда управления - 00 12
     */
    RUN_FORWARD("01", CommandCode.WRITE, "2000", "0012"),

    RUN_REVERS("01", CommandCode.WRITE, "2000", "0022"),

    STOP("01", CommandCode.WRITE, "2000", "0001"),

    SET_FREQUENCY("01", CommandCode.WRITE, "2001", "%s"),

    GET_FREQUENCY("01", CommandCode.READ, "2103", "0001"),

    SET_ACCELERATION_TIME("01", CommandCode.WRITE, "0109", "%s"),

    SET_BRAKING_TIME("01", CommandCode.WRITE, "010A", "%s"),

    GET_AMPERAGE("01", CommandCode.READ, "2104", "0001"),

    GET_TEMP("01", CommandCode.READ, "2206", "0001"),

    STOP_AFTER_SIGNAL_LOSS("01", CommandCode.WRITE, "0902", "0001"),

    SET_RUNTIME_AFTER_SIGNAL_LOSS("01", CommandCode.WRITE, "0903", "%s"),

    GET_OUTPUT_POWER("01", CommandCode.READ, "210F", "0001");

    private final String networkAddress;
    private final String commandCode;
    private final String commandAddress;
    private final String data;

    public String getByteArray() {
        return networkAddress + commandCode + commandAddress + data;
    }

    private static class CommandCode {
        public static final String WRITE = "06";
        public static final String READ = "03";
    }
}
