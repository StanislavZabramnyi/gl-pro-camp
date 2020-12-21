package com.zabramnyi;

import com.pi4j.io.gpio.Pin;
import com.pi4j.wiringpi.Gpio;

public abstract class DHTxxBase implements DHTxx {
  private static final int DHT_MAXCOUNT = 32000;
  private static final int DHT_PULSES = 41;

  private Pin pin;

  public DHTxxBase(Pin pin) {
    this.pin = pin;
  }

  public Pin getPin() {
    return pin;
  }

  public void setPin(Pin pin) {
    this.pin = pin;
  }

  @Override
  public void init() throws Exception {

    if (Gpio.wiringPiSetup() == -1) {
      throw new Exception("DHT_ERROR_GPIO");
    }
  }

  protected int[] getRawData() throws Exception {

    int pulseCounts[] = new int[DHT_PULSES * 2];
    Gpio.pinMode(pin.getAddress(), Gpio.OUTPUT);
    Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
    Gpio.digitalWrite(pin.getAddress(), Gpio.HIGH);
    Gpio.delay(500);

    Gpio.digitalWrite(pin.getAddress(), Gpio.LOW);
    Gpio.delay(20);
    Gpio.pinMode(pin.getAddress(), Gpio.INPUT);

    long count = 0;
    while (Gpio.digitalRead(pin.getAddress()) == 1) {
      if (++count >= DHT_MAXCOUNT) {

        Thread.currentThread().setPriority(Thread.NORM_PRIORITY);
        throw new Exception("DHT_ERROR_TIMEOUT");
      }
    }

    // Record pulse widths for the expected result bits.
    for (int i = 0; i < DHT_PULSES * 2; i += 2) {
      while (Gpio.digitalRead(pin.getAddress()) == 0) {
        if (++pulseCounts[i] >= DHT_MAXCOUNT) {

          Thread.currentThread().setPriority(Thread.NORM_PRIORITY);
          throw new Exception("DHT_ERROR_TIMEOUT: " + pulseCounts[i] + " pulses, " + i);
        }
      }
      while (Gpio.digitalRead(pin.getAddress()) == 1) {
        if (++pulseCounts[i + 1] >= DHT_MAXCOUNT) {
          Thread.currentThread().setPriority(Thread.NORM_PRIORITY);
          throw new Exception("DHT_ERROR_TIMEOUT: " + pulseCounts[i + 1] + " pulses, " + i);
        }
      }
    }

    Thread.currentThread().setPriority(Thread.NORM_PRIORITY);

    long threshold = 0;
    for (int i = 2; i < DHT_PULSES * 2; i += 2) {
      threshold += pulseCounts[i];
    }
    threshold /= DHT_PULSES - 1;

    int data[] = new int[5];
    for (int i = 3; i < DHT_PULSES * 2; i += 2) {
      int index = (i - 3) / 16;
      data[index] <<= 1;
      if (pulseCounts[i] >= threshold) {
        data[index] |= 1;
      }
    }
    return data;
  }
}
