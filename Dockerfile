# Используем базовый образ с JDK
FROM openjdk:17-jdk-slim

# Устанавливаем рабочую директорию
# WORKDIR /app

# Копируем файл jar вашего приложения в контейнер
COPY target/DevOps-1.0-SNAPSHOT.jar app.jar

# Указываем команду для запуска приложения
ENTRYPOINT ["java", "-jar", "app.jar"]
