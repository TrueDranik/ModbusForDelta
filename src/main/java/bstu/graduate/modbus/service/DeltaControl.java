package bstu.graduate.modbus.service;

import bstu.graduate.modbus.common.enums.CommandForDelta;
import bstu.graduate.modbus.utils.CRC16Modbus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DeltaControl {

    public void runForward() {
        PortHandler.writeBytes(CommandForDelta.RUN_FORWARD);
        PortHandler.readBytes();

        System.out.println("[DELTA]: Двигатель запущен вперед.");
    }

    public void runRevers() {
        PortHandler.writeBytes(CommandForDelta.RUN_REVERS);
        PortHandler.readBytes();

        System.out.println("[DELTA]: Двигатель запущен назад.");
    }

    public void stop() {
        PortHandler.writeBytes(CommandForDelta.STOP);
        PortHandler.readBytes();

        System.out.println("[DELTA]: Двигатель остановлен.");
    }

    public void stopAfterSignalLoss() {
        PortHandler.writeBytes(CommandForDelta.STOP_AFTER_SIGNAL_LOSS);
        PortHandler.readBytes();

        System.out.println("[DELTA]: Установлена обработка сбоя передачи сигнала!");
    }

    public void setRuntimeAfterSignalLoss(double second) {
        int v = (int) (10 * second);
        String hexSecond = String.format("%04x", v);

        PortHandler.writeBytes(CommandForDelta.SET_RUNTIME_AFTER_SIGNAL_LOSS, hexSecond);
        PortHandler.readBytes();

        System.out.println("[DELTA]: Установлен сторожевой таймер! Значение: " + second + " с.");
    }

    public void setFrequency(double frequency) {
        int v = (int) (100 * frequency);
        String hexFrequency = String.format("%04x", v);

        PortHandler.writeBytes(CommandForDelta.SET_FREQUENCY, hexFrequency);
        PortHandler.readBytes();

        System.out.println("[DELTA]: Установлена частота: " + frequency + " Гц.");
    }

    public double getFrequency() {
        PortHandler.writeBytes(CommandForDelta.GET_FREQUENCY);
        byte[] readBytes = PortHandler.readBytes();

        String hexFrequencyArray = CRC16Modbus.getBufferHexString(readBytes);
        String hexFrequency = hexFrequencyArray.substring(6, 10);
        double frequency = Integer.parseInt(hexFrequency, 16) / 100.0;

        System.out.println("[DELTA]: Частота двигателя: " + frequency + " Гц.");
        return frequency;
    }

    public double getAmperage() {
        PortHandler.writeBytes(CommandForDelta.GET_AMPERAGE);
        byte[] readBytes = PortHandler.readBytes();

        String hexAmperageArray = CRC16Modbus.getBufferHexString(readBytes);
        String hexAmperage = hexAmperageArray.substring(6, 10);
        double amperage = Integer.parseInt(hexAmperage, 16) / 10.0;

        System.out.println("[DELTA]: Ток: " + amperage + " A.");
        return amperage;
    }

    public double getTemp() {
        PortHandler.writeBytes(CommandForDelta.GET_TEMP);
        byte[] readBytes = PortHandler.readBytes();

        String hexTempArray = CRC16Modbus.getBufferHexString(readBytes);
        String hexTemp = hexTempArray.substring(6, 10);
        double temp = Integer.parseInt(hexTemp, 16) / 1.0;

        System.out.println("[DELTA]: Температура: " + temp + " °C.");
        return temp;
    }

    public double getOutputPower() {
        PortHandler.writeBytes(CommandForDelta.GET_OUTPUT_POWER);
        byte[] readBytes = PortHandler.readBytes();

        String hexOutputPowerArray = CRC16Modbus.getBufferHexString(readBytes);
        String hexOutputPower = hexOutputPowerArray.substring(6, 10);
        double outputPower = Integer.parseInt(hexOutputPower, 16) / 1.0;

        System.out.println("[DELTA]: Выходная мощность: " + outputPower + " кВт.");
        return outputPower;
    }

    public void setAccelerationTime(double accelerationTime, double accelerationTimeActual) {
        int v = (int) (10 * accelerationTimeActual);
        String hexAccelerationTime = String.format("%04x", v);

        PortHandler.writeBytes(CommandForDelta.SET_ACCELERATION_TIME, hexAccelerationTime);
        PortHandler.readBytes();

        System.out.println("[DELTA] Установлено время разгона: " + accelerationTime + " с.");
    }

    public void setBrakingTime(double brakingTime) {
        int v = (int) (10 * brakingTime);
        String hexBrakingTime = String.format("%04x", v);

        PortHandler.writeBytes(CommandForDelta.SET_BRAKING_TIME, hexBrakingTime);
        PortHandler.readBytes();

        System.out.println("[DELTA]: Установлено время торможения: " + brakingTime + " c.");
    }
}
