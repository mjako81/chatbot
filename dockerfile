FROM eclipse-temurin:23
COPY ./ ./

ENV JDK_JAVA_OPTIONS="--add-modules jdk.incubator.vector --enable-preview"

RUN ./mvnw -e clean package

CMD ["java", "-jar", "target/chatbot-0.0.1-SNAPSHOT.jar"]
