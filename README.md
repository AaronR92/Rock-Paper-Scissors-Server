# Rock-Paper-Scissors Server 🪨-📄-✂️
Spring and Kryonet server application  
To play the game you also need to download [client](https://github.com/AaronR92/Rock-Paper-Scissors-Client)

## Configuration
1. Clone this repo ``git clone https://github.com/AaronR92/Rock-Paper-Scissors-Server.git``
2. Open ``resources/application.yaml``
3. Change your database credentials and url
```yaml
spring:
  datasource:
    username: YourDatabaseUsername
    password: YourDatabasePassword
    url: YourDatabaseURL
```
#### You can also specify applications TCP port updating application.tcp value

## Compile
1. Go to project folder using ``cd``
2. Compile ``./mvnw``

## Run
1. Go to ``target`` folder if you ran compile commands
2. Run command ``java -jar rock-paper-scissors-server-0.1.jar``