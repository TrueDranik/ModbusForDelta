package bstu.graduate.modbus.common.enums;

public enum CallbackEnum {
    START_MENU,

    /**
     * Callbacks управления реле
     */
    RELAY_CONTROL_MENU,
    TURN_ON_FIRST_RELAY,
    TURN_OFF_FIRST_RELAY,

    /**
     * Callbacks управления ПЧ 'Дельта'
     */
    DELTA_FC_CONTROL_MENU,

    // Курсовая
    COURSEWORK_MENU,
    START_COURSEWORK,

    // Дипломная
    GRADUATEWORK_MENU,
}
