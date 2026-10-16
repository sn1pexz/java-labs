# Лабораторна робота №2

Тема: збірка проєкту за допомогою Gradle та робота з Java Collections Framework.
Виконав: студент групи ІА-41 Ляшенко Даниїл Олександрович

## Опис

Менеджер історії дій з обмеженням ємності. Клас `ActionHistoryManager` використовує `Deque` для збереження дій у порядку LIFO з автоматичним витісненням найстаріших записів при перевищенні заданої ємності.
Усі класи знаходяться в пакеті `ua.kpi.comsys.collections`.

### Методи

| Метод | Опис |
|---|---|
| `performAction(String)` | Додає дію на вершину, витісняє найстарішу при переповненні |
| `undo()` | Видаляє і повертає найновішу дію (або `null`) |
| `peekRecentAction()` | Повертає найновішу дію без видалення (або `null`) |
| `getHistory()` | Список дій від найновішої до найстарішої, що не змінює оригінальний |
| `getHistorySize()` | Поточна кількість дій |
| `clear()` | Очищає історію |

## Варіант

V = N mod 3 = 13 mod 3 = 1
Варіант 1: Менеджер історії дій з обмеженням ємності.

## Структура проєкту

```text
lab2/
├── gradle/
│   ├── wrapper/
│   │   ├── gradle-wrapper.jar
│   │   └── gradle-wrapper.properties
│   └── libs.versions.toml
├── src/
│   ├── main/java/ua/kpi/comsys/collections/
│   │   └── ActionHistoryManager.java
│   └── test/java/ua/kpi/comsys/collections/
│       └── ActionHistoryManagerTest.java
├── build.gradle.kts
├── settings.gradle.kts
├── gradlew
├── gradlew.bat
├── .gitignore
└── README.md
```

## Запуск тестів

Потрібно: Java 21+, Gradle (wrapper включений).

```
cd lab2
gradlew.bat test
```




