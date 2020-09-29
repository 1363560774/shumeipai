package com.shumei.pi.communication.iic;

import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CDevice;
import com.pi4j.io.i2c.I2CFactory;
import com.pi4j.platform.Platform;
import com.pi4j.platform.PlatformAlreadyAssignedException;
import com.pi4j.platform.PlatformManager;
import com.pi4j.util.Console;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Arrays;

/**
 * 注释
 *
 * @author zhaokai
 * @date 2020/9/18 11:58 下午
 */
@RestController
@RequestMapping("I2C")
public class IICController {

    // TSL2561 I2C address
    public static final int TSL2561_ADDR = 0x39; // address pin not connected (FLOATING)
    //public static final int TSL2561_ADDR = 0x29; // address pin connect to GND
    //public static final int TSL2561_ADDR = 0x49; // address pin connected to VDD

    // TSL2561 registers
    public static final byte TSL2561_REG_ID = (byte)0x8A;
    public static final byte TSL2561_REG_DATA_0 = (byte)0x8C;
    public static final byte TSL2561_REG_DATA_1 = (byte)0x8E;
    public static final byte TSL2561_REG_CONTROL = (byte)0x80;

    // TSL2561 power control values
    public static final byte TSL2561_POWER_UP = (byte)0x03;
    public static final byte TSL2561_POWER_DOWN = (byte)0x00;

    @GetMapping("/test1")
    public ResponseEntity<Object> test1() throws InterruptedException, IOException, I2CFactory.UnsupportedBusNumberException, PlatformAlreadyAssignedException {
        // create Pi4J console wrapper/helper
        // (This is a utility class to abstract some of the boilerplate code)
        final Console console = new Console();

        // print program title/header
        console.title("<-- The Pi4J Project -->", "I2C Example");

        // allow for user to exit program using CTRL-C
        console.promptForExit();

        // fetch all available busses
        try {
            int[] ids = I2CFactory.getBusIds();
            console.println("Found follow I2C busses: " + Arrays.toString(ids));
        } catch (IOException exception) {
            console.println("I/O error during fetch of I2C busses occurred");
        }

        // find available busses
        for (int number = I2CBus.BUS_0; number <= I2CBus.BUS_17; ++number) {
            try {
                I2CBus bus = I2CFactory.getInstance(number);
                console.println("Supported I2C bus " + number + " found");
            } catch (IOException exception) {
                console.println("I/O error on I2C bus " + number + " occurred");
            } catch (I2CFactory.UnsupportedBusNumberException exception) {
                console.println("Unsupported I2C bus " + number + " required");
            }
        }

        // get the I2C bus to communicate on
        I2CBus i2c = I2CFactory.getInstance(I2CBus.BUS_1);

        // create an I2C device for an individual device on the bus that you want to communicate with
        // in this example we will use the default address for the TSL2561 chip which is 0x39.
        I2CDevice device = i2c.getDevice(TSL2561_ADDR);

        // next, lets perform am I2C READ operation to the TSL2561 chip
        // we will read the 'ID' register from the chip to get its part number and silicon revision number
        console.println("... reading ID register from TSL2561");
        int response = device.read(TSL2561_REG_ID);
        console.println("TSL2561 ID = " + String.format("0x%02x", response) + " (should be 0x50)");

        // next we want to start taking light measurements, so we need to power up the sensor
        console.println("... powering up TSL2561");
        device.write(TSL2561_REG_CONTROL, TSL2561_POWER_UP);

        // wait while the chip collects data
        Thread.sleep(500);

        // now we will perform our first I2C READ operation to retrieve raw integration
        // results from DATA_0 and DATA_1 registers
        console.println("... reading DATA registers from TSL2561");
        int data0 = device.read(TSL2561_REG_DATA_0);
        int data1 = device.read(TSL2561_REG_DATA_1);

        // print raw integration results from DATA_0 and DATA_1 registers
        console.println("TSL2561 DATA 0 = " + String.format("0x%02x", data0));
        console.println("TSL2561 DATA 1 = " + String.format("0x%02x", data1));

        // before we exit, lets not forget to power down light sensor
        console.println("... powering down TSL2561");
        device.write(TSL2561_REG_CONTROL, TSL2561_POWER_DOWN);
        return ResponseEntity.status(HttpStatus.OK).body("成功");
    }

    @GetMapping("/test2")
    public ResponseEntity<Object> test2() throws InterruptedException, IOException, I2CFactory.UnsupportedBusNumberException {
        // Create I2C bus
        I2CBus bus = I2CFactory.getInstance(I2CBus.BUS_1);
        // Get I2C device, BMP280 I2C address is 0x76(108)
        I2CDevice device = bus.getDevice(0x76);

        // Read 24 bytes of data from address 0x88(136)
        byte[] b1 = new byte[24];
        device.read(0x88, b1, 0, 24);

        // Convert the data
        // temp coefficents
        int dig_T1 = (b1[0] & 0xFF) + ((b1[1] & 0xFF) * 256);
        int dig_T2 = (b1[2] & 0xFF) + ((b1[3] & 0xFF) * 256);
        if(dig_T2 > 32767)
        {
            dig_T2 -= 65536;
        }
        int dig_T3 = (b1[4] & 0xFF) + ((b1[5] & 0xFF) * 256);
        if(dig_T3 > 32767)
        {
            dig_T3 -= 65536;
        }

        // pressure coefficents
        int dig_P1 = (b1[6] & 0xFF) + ((b1[7] & 0xFF) * 256);
        int dig_P2 = (b1[8] & 0xFF) + ((b1[9] & 0xFF) * 256);
        if(dig_P2 > 32767)
        {
            dig_P2 -= 65536;
        }
        int dig_P3 = (b1[10] & 0xFF) + ((b1[11] & 0xFF) * 256);
        if(dig_P3 > 32767)
        {
            dig_P3 -= 65536;
        }
        int dig_P4 = (b1[12] & 0xFF) + ((b1[13] & 0xFF) * 256);
        if(dig_P4 > 32767)
        {
            dig_P4 -= 65536;
        }
        int dig_P5 = (b1[14] & 0xFF) + ((b1[15] & 0xFF) * 256);
        if(dig_P5 > 32767)
        {
            dig_P5 -= 65536;
        }
        int dig_P6 = (b1[16] & 0xFF) + ((b1[17] & 0xFF) * 256);
        if(dig_P6 > 32767)
        {
            dig_P6 -= 65536;
        }
        int dig_P7 = (b1[18] & 0xFF) + ((b1[19] & 0xFF) * 256);
        if(dig_P7 > 32767)
        {
            dig_P7 -= 65536;
        }
        int dig_P8 = (b1[20] & 0xFF) + ((b1[21] & 0xFF) * 256);
        if(dig_P8 > 32767)
        {
            dig_P8 -= 65536;
        }
        int dig_P9 = (b1[22] & 0xFF) + ((b1[23] & 0xFF) * 256);
        if(dig_P9 > 32767)
        {
            dig_P9 -= 65536;
        }
        // Select control measurement register
        // Normal mode, temp and pressure over sampling rate = 1
        device.write(0xF4 , (byte)0x27);
        // Select config register
        // Stand_by time = 1000 ms
        device.write(0xF5 , (byte)0xA0);
        Thread.sleep(500);

        // Read 8 bytes of data from address 0xF7(247)
        // pressure msb1, pressure msb, pressure lsb, temp msb1, temp msb, temp lsb, humidity lsb, humidity msb
        byte[] data = new byte[8];
        device.read(0xF7, data, 0, 8);

        // Convert pressure and temperature data to 19-bits
        long adc_p = (((long)(data[0] & 0xFF) * 65536) + ((long)(data[1] & 0xFF) * 256) + (long)(data[2] & 0xF0)) / 16;
        long adc_t = (((long)(data[3] & 0xFF) * 65536) + ((long)(data[4] & 0xFF) * 256) + (long)(data[5] & 0xF0)) / 16;

        // Temperature offset calculations
        double var1 = (((double)adc_t) / 16384.0 - ((double)dig_T1) / 1024.0) * ((double)dig_T2);
        double var2 = ((((double)adc_t) / 131072.0 - ((double)dig_T1) / 8192.0) *
                (((double)adc_t)/131072.0 - ((double)dig_T1)/8192.0)) * ((double)dig_T3);
        double t_fine = (long)(var1 + var2);
        double cTemp = (var1 + var2) / 5120.0;
        double fTemp = cTemp * 1.8 + 32;

        // Pressure offset calculations
        var1 = ((double)t_fine / 2.0) - 64000.0;
        var2 = var1 * var1 * ((double)dig_P6) / 32768.0;
        var2 = var2 + var1 * ((double)dig_P5) * 2.0;
        var2 = (var2 / 4.0) + (((double)dig_P4) * 65536.0);
        var1 = (((double) dig_P3) * var1 * var1 / 524288.0 + ((double) dig_P2) * var1) / 524288.0;
        var1 = (1.0 + var1 / 32768.0) * ((double)dig_P1);
        double p = 1048576.0 - (double)adc_p;
        p = (p - (var2 / 4096.0)) * 6250.0 / var1;
        var1 = ((double) dig_P9) * p * p / 2147483648.0;
        var2 = p * ((double) dig_P8) / 32768.0;
        double pressure = (p + (var1 + var2 + ((double)dig_P7)) / 16.0) / 100;

        // Output data to screen
        System.out.printf("Pressure : %.2f hPa %n", pressure);
        System.out.printf("Temperature in Celsius : %.2f C %n", cTemp);
        System.out.printf("Temperature in Fahrenheit : %.2f F %n", fTemp);
        return ResponseEntity.status(HttpStatus.OK).body("成功");
    }
}
