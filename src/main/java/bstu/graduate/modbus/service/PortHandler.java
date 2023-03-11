package bstu.graduate.modbus.service;

import bstu.graduate.modbus.common.enums.CommandForDelta;
import bstu.graduate.modbus.utils.CRC16Modbus;
import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortInvalidPortException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;

@Slf4j
@Service
public class PortHandler {
    private static SerialPort getSerialPort() {
        SerialPort[] commPorts = SerialPort.getCommPorts();

        int length = commPorts.length;
        if (length > 0) {
            return commPorts[0];
        } else {
            throw new SerialPortInvalidPortException("[COMM_PORT] Нет подключенных устройств!",
                    new Throwable().getCause());
        }
    }

    @PostConstruct
    private void openPort() {
        SerialPort commPort = getSerialPort();
        commPort.openPort();

        if (commPort.isOpen()) {
            commPort.setParity(SerialPort.EVEN_PARITY);
            log.info("[COMM_PORT]: Порт открыт.");
        } else {
            throw new SerialPortInvalidPortException("[COMM_PORT] Ошибка открытия порта! Порт не открыт!",
                    new Throwable().getCause());
        }
    }

    @PostConstruct
    private void closePort() {
        SerialPort serialPort = getSerialPort();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            serialPort.closePort();
            log.info("[COMM_PORT]: Порт закрыт.");
        }));
    }

    public static void writeBytes(CommandForDelta command, @Nullable String byteData) {
        SerialPort serialPort = getSerialPort();
        String portCommand = getPortCommand(command, byteData);
        byte[] sendBuf = CRC16Modbus.getSendBuffer(portCommand);
        int length = sendBuf.length;
        serialPort.writeBytes(sendBuf, length);

        String bufHexStr = CRC16Modbus.getBufferHexString(sendBuf);
        log.info("[COMM_PORT/WRITE]: Записан массив: {}", bufHexStr);
    }

    public static void writeBytes(CommandForDelta command) {
        writeBytes(command, null);
    }

    public static int readBytes() throws InterruptedException {
        SerialPort serialPort = getSerialPort();
        validateReadBytes();

        byte[] readBuffer = new byte[serialPort.bytesAvailable()];
        int readBytes = serialPort.readBytes(readBuffer, readBuffer.length);
        log.info("[COMM_PORT/READ]: Прочитано {} байт.", readBytes);

        return readBytes;
    }

    private static String getPortCommand(CommandForDelta command, String byteData) {
        String portCommand;
        if (StringUtils.hasText(byteData)) {
            portCommand = command.getBytes().formatted(byteData);
        } else {
            portCommand = command.getBytes();
        }

        return portCommand;
    }

    private static void validateReadBytes() throws InterruptedException {
        SerialPort serialPort = getSerialPort();

        int i = 0;
        while (serialPort.bytesAvailable() == 0 && i < 5) {
            i++;
            log.info("[COMM_PORT/READ]: Ожидание ответа..");
            Thread.sleep(1000);
        }
    }
}
