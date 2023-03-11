package bstu.graduate.modbus.common.enums;

import lombok.Getter;

public enum CommandForDelta {
    @Deprecated
    TURN_ON("01050000FF00"),
    @Deprecated
    TURN_OFF("010500000000"),
    /**
     * Запуск двигателя. Биты такие-то...
     */
    RUN_FORWARD("010620000012"),

    /**
     * Остановить двигатель. Биты такие-то...
     */
    STOP("010620000001"),

    SET_FREQUENCY("01062000%s"),
    GET_FREQUENCY("01062000%s");


    @Getter
    private final String bytes;
    CommandForDelta(String bytes) {
        this.bytes = bytes;
    }
}
