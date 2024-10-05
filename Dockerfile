FROM openjdk:17-jdk-slim

ARG JAR_FILE=soluPrinterPlugin.jar

COPY target/${JAR_FILE} /app/${JAR_FILE}

CMD ["java", "-jar", "/app/soluPrinterPlugin.jar"]