#include <SPI.h>
#include <RH_RF95.h>

#define LED 10

RH_RF95 rf95;

void setup() {
  pinMode(LED, OUTPUT);     
  Serial.begin(9600);
  while (!Serial);
  if (!rf95.init()) {
    Serial.println("Initialization failed.");  
    return;
  }
  rf95.setFrequency(915);
  rf95.setTxPower(20, false);
}

void loop() {
  if (rf95.available()) {
    uint8_t buf[RH_RF95_MAX_MESSAGE_LEN];
    uint8_t len = sizeof(buf);
    if (rf95.recv(buf, &len)) {       
      digitalWrite(LED, HIGH); 
      Serial.print("DATALOG: ");
      Serial.println((char*)buf);
      
      // Send a reply
      uint8_t data[] = "OK";
      rf95.send(data, sizeof(data));
      rf95.waitPacketSent();
       digitalWrite(LED, LOW);
    }
    else {
      Serial.println("ERROR: failed to receive data.");
    }
  }
}


