package bstu.graduate.modbus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan("bstu.graduate.modbus.common.properties")
public class ModbusForDeltaApplication {

    public static void main(String[] args) {
        SpringApplication.run(ModbusForDeltaApplication.class, args);
    }

}
