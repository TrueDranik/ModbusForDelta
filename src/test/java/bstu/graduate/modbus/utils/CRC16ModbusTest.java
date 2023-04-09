package bstu.graduate.modbus.utils;

import bstu.graduate.modbus.common.enums.CommandForDelta;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CRC16ModbusTest {
    @Test
    void testSendBuffer() {
        byte[] firstResult = {1, 6, 32, 0, 0, 18, 2, 7};
        byte[] firstSendBuffer = CRC16Modbus.getSendBuffer(CommandForDelta.RUN_FORWARD.getByteArray());
        assertArrayEquals(firstResult, firstSendBuffer);

        byte[] secondResult = {1, 5, 0, 0, -1, 0, -116, 58};
        byte[] secondSendBuffer = CRC16Modbus.getSendBuffer(CommandForDelta.STOP.getByteArray());
        assertArrayEquals(secondResult, secondSendBuffer);
    }

    @Test
    void testBufferHexString() {
        byte[] firstSendBuffer = {1, 6, 32, 0, 0, 18};
        String bufferHexString = CRC16Modbus.getBufferHexString(firstSendBuffer);
        String result = CommandForDelta.RUN_FORWARD.getByteArray();
        assertEquals(result, bufferHexString);
    }
}