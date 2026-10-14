# Лабораторна робота №1

Тема: сучасні можливості Java та Data-Oriented Programming (DOP).
Виконала: Студентка групи ІА-41 Чебакова В.С.
## Опис

Фінансова система транзакцій. Дані описані через `sealed interface Transaction` і три `record`: `Deposit`, `Withdrawal`, `Transfer`. Логіка винесена в клас `TransactionProcessor`, метод `process()` повертає текст через `switch` з pattern matching та Text Block.

Усі класи знаходяться в пакеті `ua.kpi.comsys.dop`.

## Варіант

V = N mod 3 = 19 mod 3 = 1

Варіант 1: Фінансова система транзакцій.

## Запуск тестів

Java 21 і Maven. У корені проєкту:

```
mvn test
```