# cart-service

Содержит микросервис для работы с корзинами продуктов.

## Запуск приложения

### Сетевые порты

| Номер | Описание             |
|-------|----------------------|
| 8080  | Порт основного API.  |
| 9090  | Порт служебного API. |

### Существующие переменные окружения

| Имя                             | Описание                                                                | Значение по умолчанию |
|---------------------------------|-------------------------------------------------------------------------|-----------------------|
| HTTP_BASE_PATH                  | Базовый путь основного API.                                             | /v1/cart              |
| HTTP_MAX_HEADER_SIZE            | Максимально допустимый размер заголовка запроса.                        | 8KB                   |
| HTTP_MANAGEMENT_BASE_PATH       | Базовый путь служебного API.                                            | /v1/cart              |
| CONFIG_SERVER_URL               | Ссылка на сервер конфигураций.                                          |                       |
| MONGODB_HOST                    | Хост базы данных MongoDB.                                               |                       |
| MONGODB_PORT                    | Порт базы данных MongoDB.                                               | 27017                 |
| MONGODB_USERNAME                | Имя пользователя, необходимое для подключения к базе данных MongoDB.    |                       |
| MONGODB_PASSWORD                | Пароль пользователя, необходимый для подключения к базе данных MongoDB. |                       |
| MONGODB_AUTHENTICATION_DATABASE | Имя базы данных MongoDB для аутентификации.                             | admin                 |
| MONGODB_DATABASE                | Имя базы данных MongoDB.                                                | cart                  |
| KAFKA_BOOTSTRAP_SERVERS         | Список URL для подключения к Apache Kafka.                              |                       |
| KAFKA_CONSUMER_GROUP            | Имя группы потребителей в Apache Kafka.                                 | cart-service          |
| KC_ISSUER_URI                   | Ссылка на Keycloak область.                                             |                       |
| KC_USERNAME_ATTRIBUTE           | Имя атрибута Keycloak для идентификации пользователей.                  | sub                   |
| KC_CLIENT_ID                    | Идентификатор клиента Keycloak.                                         |                       |
| KC_CLIENT_SECRET                | Секрет клиента Keycloak.                                                |                       |
