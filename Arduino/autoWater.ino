#include <ArduinoHttpClient.h>
#include <WiFiS3.h>
#include "settings.h"
char ssid[] = SECRET_SSID;
char password[] = SECRET_PASSWORD;

const char serverAddress[] = "lionfish-app-7nn2h.ondigitalocean.app";  // server address
int port = 443;

WiFiSSLClient wifi;
HttpClient client = HttpClient(wifi, serverAddress, port);


int powerPin = 8;
int moistInputPin1 = 2;
int moistInputPin2 = 3;
int moistInputPin3 = 4;
int moistInputPin4 = 11;
int moistInputPin5 = 12;
int moistInputPin6 = 13;

int moistValue1;
int moistValue2;
int moistValue3;
int moistValue4;
int moistValue5;
int moistValue6;
int DHpin = 10;  // input/output pin// input/output pin

byte dat[5];
byte read_data() {
  byte data;
  for (int i = 0; i < 8; i++) {
    if (digitalRead(DHpin) == LOW) {
      while (digitalRead(DHpin) == LOW)
        ;                     // wait 50us;
      delayMicroseconds(30);  // Duration of high level determine whether data is 0 or 1
      if (digitalRead(DHpin) == HIGH)
        data |= (1 << (7 - i));  //High in the former, low in the post;
      while (digitalRead(DHpin) == HIGH)
        ;  //Data '1', waiting for next bit
    }
  }
  return data;
}
void setup() {
  WiFi.begin(ssid, password);
  while (WiFi.status() != WL_CONNECTED) {
    Serial.print(".");
    delay(1000);  // Vänta en sekund innan du försöker igen
  }

  Serial.begin(9600);
  pinMode(DHpin, OUTPUT);
  pinMode(powerPin, OUTPUT);
  pinMode(moistInputPin1, INPUT);
  pinMode(moistInputPin2, INPUT);
  pinMode(moistInputPin3, INPUT);
  pinMode(moistInputPin4, INPUT);
  pinMode(moistInputPin5, INPUT);
  pinMode(moistInputPin6, INPUT);
}
void loop() {
  start_test();
  Serial.println("fukt och temp test klart");
  delay(10);
  getDataFromSensors();
  Serial.println("jordfukt test klart");
  postMoistData(client);
  postTemperatureData(client);
  delay(1800000);
}

void postMoistData(HttpClient client) {
  String jsonBody = "{";
  jsonBody += "\"1\": " + String(moistValue1) + ", ";
  jsonBody += "\"2\": " + String(moistValue2) + ", ";
  jsonBody += "\"3\": " + String(moistValue3) + ", ";
  jsonBody += "\"4\": " + String(moistValue4) + ", ";
  jsonBody += "\"5\": " + String(moistValue5) + ", ";
  jsonBody += "\"6\": " + String(moistValue6) + "}";

  client.beginRequest();
  client.post("/updateplant");

  client.sendHeader("Content-Type: application/json");
  client.sendHeader("Content-Length: " + String(jsonBody.length()));
  client.beginBody();
  client.print(jsonBody);

  client.endRequest();
  delay(1000);
  int statusCode = client.responseStatusCode();
  if (statusCode != 200) {
    Serial.print("Error: ");
    Serial.println(statusCode);
  }
  Serial.println(statusCode);
  Serial.println(jsonBody);
  client.stop();
}
void postTemperatureData(HttpClient client) {
  String jsonBody = "{";
  jsonBody += "\"tempratur\": " + String(dat[2]) + "." + String(dat[3]) + ", ";
  jsonBody += "\"fuktighet\": " + String(dat[0]) + "." + String(dat[1]) + "}";

  client.beginRequest();
  client.post("/temp");
  client.sendHeader("Content-Type: application/json");
  client.sendHeader("Content-Length: " + String(jsonBody.length()));
  client.beginBody();
  client.print(jsonBody);

  client.endRequest();
  Serial.println(jsonBody);
  int statusCode = client.responseStatusCode();
  if (statusCode != 200) {
    Serial.print("Error: ");
    Serial.println(statusCode);
  }
  Serial.println(statusCode);
  client.stop();
}

void start_test() {
  digitalWrite(DHpin, LOW);  //Pull down the bus to send the start signal;
  delay(30);                 //The delay is greater than 18 ms so that DHT 11 can detect the start signal;
  digitalWrite(DHpin, HIGH);
  delayMicroseconds(40);  //Wait for DHT11 to respond;
  pinMode(DHpin, INPUT);
  while (digitalRead(DHpin) == HIGH)
    ;
  delayMicroseconds(80);  //The DHT11 responds by pulling the bus low for 80us;
  if (digitalRead(DHpin) == LOW)
    ;
  delayMicroseconds(80);       //DHT11 pulled up after the bus 80us to start sending data;
  for (int i = 0; i < 4; i++)  //Receiving data, check bits are not considered;
    dat[i] = read_data();
  pinMode(DHpin, OUTPUT);
  digitalWrite(DHpin, HIGH);  //After release of bus, wait for host to start next signal
}

void clearMonitor() {
  for (int i = 0; i < 50; i++) {
    Serial.println();
  }
}

void printNetwork() {
  Serial.println("-----------------");
  Serial.print("Du är nu ansluten till: ");
  Serial.println(WiFi.SSID());
  IPAddress ip = WiFi.localIP();
  Serial.print("IP adress: ");
  Serial.println(ip);
  Serial.println("-----------------");
}

void getDataFromSensors() {
  digitalWrite(powerPin, HIGH);

  delay(2000);
  moistValue1 = digitalRead(moistInputPin1);
  moistValue2 = digitalRead(moistInputPin2);
  moistValue3 = digitalRead(moistInputPin3);
  moistValue4 = digitalRead(moistInputPin4);
  moistValue5 = digitalRead(moistInputPin5);
  moistValue6 = digitalRead(moistInputPin6);
  delay(1000);
  digitalWrite(powerPin, LOW);
}
