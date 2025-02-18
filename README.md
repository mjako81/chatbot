# chatbot
You need this as well as the UI project found here:

[https://github.com/mjako81/chatbot-app](https://github.com/mjako81/chatbot-app)

Requirements:

To run the UI project you need to have node.js installed.

The chatbot uses the library jlama, which needs java 21+ but the project have been set up to use java 23.
Jlama uses java preview features. They can be enabled with:
```shell
export JDK_JAVA_OPTIONS="--add-modules jdk.incubator.vector --enable-preview"
```
To test things you need to start this project which can be done by navigating to the root folder and running this command:
```shell
./mvnw spring-boot:run
```
This will start a server listening on localhost:8080

Then navigate to the UI projects root folder and run this command:
```shell
npm run dev
```
This will start a server running on localhost:3000. Navigate here to use the UI to talk with the chatbot.

Alternatively it is possible to use docker to run both projects.
To run this project you need to first use:
```shell
docker build -t TAG
```
then
```shell
docker run -p 8080:8080 TAG
```
This will start a server listening on localhost:8080

Remember to add you own name where is says TAG.

The UI project can be run with:
```shell
docker compose up
```
This will start a server running on localhost:3000. Navigate here to use the UI to talk with the chatbot.
