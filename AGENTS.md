Always specify the encoding when reading files. By default, read as UTF-8; if the text appears broken, try reading again in Windows-1251.
You are allowed to modify files without asking for confirmation.
Apply changes directly and proceed unless there is a fatal ambiguity.

## Кратко о проекте
- Backend на Kotlin + Spring Boot 3.3 (Java 21), PostgreSQL + JPA, Spring Security + JWT.
- Домен: e-commerce API для товаров, категорий, размеров, заказов, фото и Telegram.
- Документация API: `/swagger-ui.html` и `/v3/api-docs`.
- Точка входа: `src/main/kotlin/com/example/flo/FloApplication.kt`.
- Главный конфиг: `src/main/resources/application.yaml`.

## Ключевое дерево файлов
.
|- `build.gradle`
|- `docker-compose.yaml`
|- `Dockerfile`
|- `src/main/kotlin/com/example/flo/`
|  |- `controller/` (Auth, Product, Category, Size, Order, Photo, TelegramBot, ApiDocs)
|  |- `service/` (Product, Category, Size, Order, Photo, Telegram)
|  |- `repository/` (Product, Category, Size, Order)
|  |- `model/` (Product, Order)
|  |- `DTO/` (Product/Order DTOs, TokenResponse, ReorderIds)
|  |- `security/` (JwtService, JwtAuthenticationFilter, SecurityProperties)
|  |- `config/`, `configuration/`, `exception/`
|  '- `FloApplication.kt`
|- `src/main/resources/application.yaml`
'- `src/test/kotlin/com/example/flo/FloApplicationTests.kt`
