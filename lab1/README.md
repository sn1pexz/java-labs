# Лабораторна робота №1

Тема: сучасні можливості Java та Data-Oriented Programming (DOP).
Виконав: студент групи ІА-41 Ляшенко Даниїл

## Опис

Фінансова система транзакцій, реалізована з використанням підходу Data-Oriented Programming у Java 21.

Дані описані через `sealed interface Transaction` і три `record`:
- `Deposit(String accountId, BigDecimal amount)` - депозит на рахунок
- `Withdrawal(String accountId, BigDecimal amount, String category)` - зняття коштів
- `Transfer(String fromAccount, String toAccount, BigDecimal amount)` - переказ між рахунками

Кожен record містить валідацію у compact constructor: текстові поля не можуть бути `null` або порожніми, `amount` має бути більше 0.

Логіка винесена в окремий клас `TransactionProcessor`. Метод `process()` використовує `switch expression` з pattern matching (без `default`) та повертає форматований рядок через Text Block (`"""`).

Усі класи знаходяться в пакеті `ua.kpi.comsys.dop`.


## Варіант

V = N mod 3 = 13 mod 3 = 1

Варіант 1: Фінансова система транзакцій.

## Структура проєкту

```
lab1/
├── src/
│   ├── main/java/ua/kpi/comsys/dop/
│   │   ├── Transaction.java
│   │   └── TransactionProcessor.java
│   └── test/java/ua/kpi/comsys/dop/
│       └── TransactionProcessorTest.java
├── build.gradle.kts
└── README.md
```

## Запуск тестів

Потрібно: Java 21+, Gradle (wrapper включений у проєкт).

```
cd lab1
gradlew.bat test
```
