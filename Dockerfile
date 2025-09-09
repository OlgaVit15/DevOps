# Используем базовый образ с JDK
# FROM openjdk:17-jdk-slim

# # Устанавливаем рабочую директорию
# WORKDIR /app
#
# # Копируем файл jar вашего приложения в контейнер
# COPY target/DevOps-1.0-SNAPSHOT.jar app.jar
#
# # Указываем команду для запуска приложения
# ENTRYPOINT ["java", "-jar", "app.jar"]
# На этот раз нам требуется образ, содержащий maven, при помощи
# ключевого слова as мы указываем псевдоним для контейнера сборки,
# чтобы при его помощи в дальнейшем обращаться к контейнеру

FROM maven:3.9.4-eclipse-temurin-17 as build

# Собирать проект будем в /build

WORKDIR /build

# Теперь необходимо скопировать необходимые для сборки проекта файлы в конейнер

COPY src src
COPY pom.xml pom.xml

# И запустить сборку проекта. Загружаемые библиотеки желательно кэшировать между
# сборками,для этого нужно добавить --mount=type=cache,target=/root/.m2 к RUN

RUN --mount=type=cache,target=/root/.m2 mvn clean package dependency:copy-dependencies -DincludeScope=runtime

