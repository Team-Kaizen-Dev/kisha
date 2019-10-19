#include <SoftwareSerial.h>

SoftwareSerial bluetoothSerial(3, 4);

void setup() {
  Serial.begin(9600);
  bluetoothSerial.begin(9600);
  Serial.println("Ready!");
}

void loop() { 
  bluetoothSerial.listen();
  if (bluetoothSerial.available()) {
    String data = bluetoothSerial.readString();
    byte bytes[data.length()];
    data.getBytes(bytes, data.length());
  }
  if (Serial.available()) {
    String data = Serial.readString();
    bluetoothSerial.println(data);
  }
}
