#!/bin/bash

# Переходим в директорию с docker-compose.yml (убедитесь, что путь правильный!)
cd /home/ubuntu/app

# Останавливаем и удаляем существующие контейнеры (если они есть)
sudo docker-compose down

# Запускаем Docker Compose в detached режиме (-d)
sudo docker-compose up -d

echo "Docker Compose запущен!"
