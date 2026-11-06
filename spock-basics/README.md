# spock-basics

Те же тесты, что в проекте на JUnit 5, но на Groovy + Spock. Тестируемый класс `BankAccount` скопирован из
репозитория `junit-bankaccount-tests` без изменений.

## Запуск

    mvn -pl spock-basics test

## Задачи

Перенести все тест кейсы `BankAccount` блока 1 из `test-cases.md` JUnit-проекта.

- [x] 1.1 Базовые операции 
- [x] 1.2 Исключения: свести повторяющиеся кейсы в таблицы `where:`
- [x] 1.3 Заморозка счёта (использовать `setup()` там, где подготовка одинаковая)
- [x] 1.4 Переводы: проверка двух счетов через `verifyAll { }`
- [x] Отключённые тесты с багами: аннотация `@PendingFeature(reason = "BUG-N: ...")` вместо `@Disabled`

## Что освоить

| JUnit 5 | Spock |
|---------|-------|
| `@Test` | метод с именем в кавычках |
| Arrange / Act / Assert | `given:` / `when:` / `then:` |
| `assertEquals(a, b)` | `a == b` |
| `assertThrows` | `thrown(Type)` |
| `@ParameterizedTest` | блок `where:` с таблицей |
| `assertAll` | `verifyAll { }` |
| `@Disabled` | `@Ignore` |
