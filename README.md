# T1-homework-spring-security

## Состав проекта

Spring Boot-проект с авторизацией по токенам и поддержкой ролей:

- `JwtService` — генерация и валидация access/refresh токенов
- `JwtAuthFilter` — кастомный фильтр авторизации
- `InMemoryUserDetailsService` — in-memory хранилище пользователей
- `RedisTokenBlacklistService` — хранение отозванных access токенов с TTL
- `AuthController` — регистрация, логин, logout, refresh
- `AdminController` — проверка админа
---

### 1. Конфигурация application.properties

```
server.port=8080

spring.data.redis.host=localhost
spring.data.redis.port=6379

jwt.secret=aVeryLongAndSecureSecretKeyThatShouldBeStoredSafelyOutsideTheCodebase

jwt.expiration.access=900000

jwt.expiration.refresh=604800000
```

- `access`/`refresh` — задают TTL токенов

### 2. Конфигурация docker-compose.yml

```
version: '3.8'

services:
  redis:
    image: redis:7-alpine
    container_name: redis
    ports:
      - "6379:6379"
    restart: unless-stopped
```

Redis используется для хранения отозванных токенов.

### 3. Запуск приложения

Осуществляется запуском `T1HomeworkSpringSecurityApplication`

### 4. Эндпоинты

#### POST /api/auth/register

Регистрация нового пользователя:

```
{
"login": "new_admin",
"email": "new_admin@example.com",
"password": "new_admin123",
"roles": ["ADMIN"]
}
```

#### POST /api/auth/login

Аутентификация:

```
{
"login": "new_admin",
"password": "new_admin123"
}
```

#### POST /api/auth/logout

Отзывает access-токен (кладёт в Redis blacklist):  
**Header:**
```
Authorization: Bearer <accessToken>
```

#### POST /api/auth/refresh

Получение нового access токена по refresh:

```
{
"refreshToken": "<refreshToken>"
}
```

#### GET /api/admin/secret

Доступен только пользователям с `ROLE_ADMIN`  
**Header:**
```
Authorization: Bearer <accessToken>
```

### 5. Проверка Redis blacklist

После выполнения logout в терминал написать:
```
docker exec -it redis redis-cli
KEYS * 
```

### 6. Тесты

Для тестрирования необходимо запустить AuthServiceTest
