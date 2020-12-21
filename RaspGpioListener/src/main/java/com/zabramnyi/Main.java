package com.zabramnyi;

import com.pi4j.io.gpio.RaspiPin;

public class Main {

  private static final int DHT_WAIT_INTERVAL = 2000;

  public static void main(String[] args) {
    DHTxx dht22 = new DHT22(RaspiPin.GPIO_04);
    System.out.println(dht22);
    try {
      dht22.init();
      for (int i = 0; i < 10; i++) {
        try {
          System.out.println(dht22.getData());
          Thread.sleep(DHT_WAIT_INTERVAL);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    } catch (Exception e1) {
      e1.printStackTrace();
    }
  }
}
