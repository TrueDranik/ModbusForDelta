package bstu.graduate.modbus.common.enums;

public enum CallbackEnum {
    START_MENU,

    /**
     * Callbacks управления ПЧ 'Дельта'
     */
    CONTROL_COMMAND,
    READING_STATUS_COMMAND,

    RUN_FORWARD,
    RUN_REVERS,
    STOP,
}
