#include <SPI.h>
#include <RH_RF95.h>

#define OK_LED 7
#define ERROR_LED 3

RH_RF95 rf95;

void setup() {
  pinMode(OK_LED, OUTPUT);
  pinMode(ERROR_LED, OUTPUT);

  if (!rf95.init()) {
    while (true) {
      digitalWrite(ERROR_LED, HIGH);
      delay(2000);
      digitalWrite(ERROR_LED, LOW);
      delay(2000);
    }
  } else {
    rf95.setFrequency(915);
    digitalWrite(OK_LED, HIGH);
    delay(500);
    digitalWrite(OK_LED, LOW);
    delay(500);
  }
  rf95.setTxPower(20, false);
}

void loop() {
  uint8_t data[] = "2-18-10.34582938482393,122.163423582";
  rf95.send(data, sizeof(data));

  rf95.waitPacketSent();
  // Now wait for a reply
  uint8_t buf[RH_RF95_MAX_MESSAGE_LEN];
  uint8_t len = sizeof(buf);

  if (rf95.waitAvailableTimeout(15000)) {
    if (rf95.recv(buf, &len)) {
      //      Serial.print("got reply: ");
      //      Serial.println((char*)buf);
      digitalWrite(OK_LED, HIGH);
      delay(1000);
      digitalWrite(OK_LED, LOW);
      delay(1000);
    } else {
      //      Serial.println("recv failed");
      digitalWrite(ERROR_LED, HIGH);
      delay(1000);
      digitalWrite(ERROR_LED, LOW);
      delay(1000);
    }
  } else {
    //    Serial.println("No reply, is rf95_server running?");
    digitalWrite(ERROR_LED, HIGH);
    delay(300);
    digitalWrite(ERROR_LED, LOW);
    delay(300);
  }
  delay(3000);
}


