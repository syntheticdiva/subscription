## Микросервис, который предоставляет REST API для управления пользователями и их подписками на сервисы.

## Инструкция по запуску
Скачайте zip-архив с gitHub, распакуйте, запустите, в терминале введите команду:
### docker-compose up --build
после сборки проекта перейдите по адресу:
### http://localhost:8080/swagger-ui/index.html#/

## Эндпоинты:
### POST /users - создать пользователя
### GET /users/{id} - получить информацию о пользователе
### PUT /users/{id} - обновить пользователя
### DELETE /users/{id} - удалить пользователя
### POST /users/{userId}/subscriptions - добавить подписку
### GET /users/{userId}/subscriptions - получить подписки пользователя
### DELETE /users/{userId}/subscriptions/{subscriptionsId} - удалить подписку
### GET /users{userId}/subscriptions/top - получить ТОП-3 популярных подписок
### GET /api/services - получить все доступные сервисы 