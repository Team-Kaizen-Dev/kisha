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
    char data = bluetoothSerial.read();
    Serial.write(data);
    Serial.println();
  }
  if (Serial.available()) {
    char data = Serial.read();
    bluetoothSerial.write(data);
    Serial.println();
  }
}
