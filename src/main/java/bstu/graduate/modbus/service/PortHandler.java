package bstu.graduate.modbus.service;

import bstu.graduate.modbus.common.enums.CommandForDelta;
import bstu.graduate.modbus.utils.CRC16Modbus;
import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortInvalidPortException;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@Slf4j
@Service
public class PortHandler {

    @Getter
    @Setter
    private static SerialPort[] commPorts = SerialPort.getCommPorts();

    private static SerialPort getSerialPort() {
        SerialPort[] commPorts = getCommPorts();

        int length = commPorts.length;
        if (length > 0) {
            return commPorts[0];
        } else {
            throw new SerialPortInvalidPortException("[PORT] Нет подключенных устройств!",
                    new Throwable().getCause());
        }
    }

    @PostConstruct
    private void openPort() {
        SerialPort commPort = getSerialPort();
        commPort.openPort();

        if (commPort.isOpen()) {
            DeltaControl deltaControl = new DeltaControl();

            commPort.setRs485ModeParameters(true, false, 0, 0);
            commPort.setParity(SerialPort.EVEN_PARITY);
            commPort.setNumStopBits(SerialPort.ONE_STOP_BIT);
            commPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_BLOCKING, 100, 100);

            deltaControl.setRuntimeAfterSignalLoss(0.1);
            deltaControl.stopAfterSignalLoss();

            System.out.println("[PORT]: Порт открыт.");
        } else {
            throw new SerialPortInvalidPortException("[PORT] Ошибка открытия порта! Порт не открыт!",
                    new Throwable().getCause());
        }
    }

    @PostConstruct
    private void shutdownHookStopDeltaAndClosePort() {
        SerialPort serialPort = getSerialPort();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            DeltaControl deltaControl = new DeltaControl();
            deltaControl.setBrakingTime(5.0);
            deltaControl.stop();

            serialPort.closePort();
            System.out.println("[PORT]: Порт закрыт.");
        }));
    }

    public static void writeBytes(CommandForDelta command) {
        writeBytes(command, null);
    }

    public static void writeBytes(CommandForDelta command, @Nullable String byteData) {
        SerialPort serialPort = getSerialPort();
        String portCommand = getPortCommand(command, byteData);
        byte[] sendBuf = CRC16Modbus.getSendBuffer(portCommand);

        OutputStream outputStream = serialPort.getOutputStream();
        try (outputStream) {
            outputStream.write(sendBuf);
            outputStream.flush();
        } catch (IOException e) {
            log.error("[PORT]: Ошибка записи массива данных!", e);
        }

        String bufHexStr = CRC16Modbus.getBufferHexString(sendBuf);
        System.out.println("[PORT/WRITE]: Записан массив: " + bufHexStr);
    }

    public static byte[] readBytes() {
        SerialPort serialPort = getSerialPort();

        int read = 0;
        byte[] readBuffer = new byte[8];
        try (InputStream is = serialPort.getInputStreamWithSuppressedTimeoutExceptions()) {
            read = is.read(readBuffer);
        } catch (IOException e) {
            log.error("[PORT]: Ошибка чтения массива данных!", e);
        }

        System.out.println("[PORT/READ]: Прочитано " + read + " байт.");
        return readBuffer;
    }

    private static String getPortCommand(CommandForDelta command, String byteData) {
        String portCommand;
        if (byteData == null) {
            portCommand = command.getByteArray();
        } else {
            portCommand = command.getByteArray().formatted(byteData);
        }

        return portCommand;
    }

    private static void validateReadBytes() {
        SerialPort serialPort = getSerialPort();

        int i = 0;
        while (serialPort.bytesAvailable() == 0 && i < 5) {
            i++;
            log.info("[COMM_PORT/READ]: Ожидание ответа..");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                log.warn(e.getMessage());
                Thread.currentThread().interrupt();
            }
        }
    }
}
