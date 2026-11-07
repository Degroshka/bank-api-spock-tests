# bank-api-spock-tests

Учебный проект по автоматизации тестирования REST API на **Groovy + Spock + REST Assured** с проверкой данных в PostgreSQL.

Сервис и первоначальная тестовая документация подготовлены ИИ-ассистентом для тренировки. Автотесты, дополнительные тест-кейсы и баг-репорты написаны мной.

## Структура

| Папка | Что внутри                                                                              |
|-------|-----------------------------------------------------------------------------------------|
| `spock-basics` | Тесты `BankAccount` на Spock (тот же класс, что в проекте на JUnit 5)                   |
| `service` | REST-сервис на Spring Boot + PostgreSQL                                                 |
| `api-tests` | Автотесты API на Spock + REST Assured, проверка БД через SQL (в работе, пока 3 примера) |
| `docs` | Требования, тест-план, чек-лист, тест-кейсы API, баг-репорты                            |

Предыдущий этап (JUnit 5): репозиторий `junit-bankaccount-tests`.

## Версии

Java 17+, Maven 3.9+, Docker. Groovy 4.0, Spock 2.4, REST Assured 5.5, Spring Boot 3.5, PostgreSQL 16.

## Запуск

1. Поднять базу данных:

       docker compose up -d

2. Запустить сервис (из корня проекта) или класс `BankApplication` в IntelliJ IDEA:

       mvn -pl service spring-boot:run
(Если порт 5432 занят, скопируйте `.env.example` в `.env` и поменяйте `DB_PORT`)

   Swagger UI: http://localhost:8080/swagger-ui.html

3. Запустить API-тесты:

       mvn -pl api-tests test -DskipApiTests=false

   Параметры: `-DbaseUrl=http://localhost:8080`, `-DdbUrl=jdbc:postgresql://localhost:5433/bank`.

4. Запустить тесты `spock-basics` (БД не нужна):

       mvn -pl spock-basics test

Команда `mvn test` из корня безопасна: API-тесты в ней пропускаются, потому что им нужны запущенные сервис и БД.

## Дорожная карта

- [x] JUnit 5: unit-тесты, тест-кейсы, баг-репорты (репозиторий `junit-bankaccount-tests`)
- [x] Spock: перенос тестов `BankAccount` (`spock-basics`)
- [ ] Ручное тестирование сервиса по `docs/checklist.md`
- [ ] API-тесты по `docs/api-test-cases.md`
- [ ] Баг-репорты по найденным дефектам
- [ ] CI на GitHub Actions
- [ ] Нагрузочный сценарий в JMeter
