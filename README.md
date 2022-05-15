Запуск сервера

```
> server / reStart
```

```
> server / Test / compile
```

Миграция БД

```
> server/flywayInfo
```

```
> server/flywayMigrate
```

Тестирование рест в консоли

```
> curl http://localhost:8081/todos/user
```

Adding your SSH key to the ssh-agent

```
> eval "$(ssh-agent -s)"
> ssh-add /c/Programming/id_rsa
```

`git log --pretty=format:"%h - %an, %ar : %s" --since=1.week --graph`

Pg super user - 1111

TODO

- [ ] тестирование docker image postgres; интеграционный тест чтобы не аклиенте отображались нужные записи в списке (поправить тесты)
- [x] use flyway migrations
- [ ] use scalaCheckStyle
- [x] scala test for queries
- [ ] add swagger

NEXT

- [ ] val transactor создавать в db object?
- [ ] сделать слой запросов val findAllTodosQuery: doobie.Query0[Todo]
      слой ConnectionIO - val findAllTodos: doobie.ConnectionIO[List[Todo]]
      слой Todo - IO[List[Todo]]
- [ ] add pureconfig, данные для входа
- [ x] из todos(n: Name) удалить n, delete jokeRoutes, rename serverApiRoutes
- [ ] should use ember client builder?
- [ ] кнопки добавить, удалить, не/выполнено
- [ ] когда , для чего использовать Stream[ConnectionIO, A]
- [ ] интерпретатор от ConnectionOp ~> M - Стратегия транзакций, определяющая стратегию настройки, обработки ошибок и очистки, связанную с каждым взаимодействием с базой данных
      For example, to create a transactor that is the same as xa but always rolls back (for testing perhaps) you can say:
      `val testXa = Transactor.after.set(xa, HC.rollback)`
- [ ] Пул соединений — это объект, управляемый на протяжении всего времени существования, который необходимо корректно закрыть, поэтому он управляется как ресурс. Программа, использующая HikariTransactor, обычно использует IOApp.

* [ ] почему выполняется 4 запросы списка users по сети - это библиотека выполняет периодические запросы на сервер? сделать настройку react-query 1 раз в 5 мин
* [ ] ? нужно ли использовать React Most Wanted is a set of starter kits
      https://github.com/TarikHuber/react-most-wanted
      https://mui.com/material-ui/getting-started/example-projects/#free
* [ ] авторизация, аутентификация, keycloak, google

NEXT2

- [ ] when use tapir
- [ ] подключить organizeImports
- [ ] вывести сообщение при ошибке начитки данных (react-query) `if (error) return <div>An error has occurred: {error}</div>;`
- [ ] custom fetch with json decoding ? (react-query)
- [ ] [error] (server / flywayMigrate) org.flywaydb.core.api.FlywayException: Found non-empty schema(s) "public" without schema history table! Use baseline() or set baselineOnMigrate to true to initialize the schema history table.
- [ ] fix [warn] Unable to resolve location filesystem:src/main/resources/db/migration
- [ ] flyway если нет БД создать

- [ ] fix version
      flywayBaselineVersion := "5"

- [ ] fix-warning

> In the project doing the aggregating, the root project in this case, you can control aggregation per-task. For example, to avoid aggregating the update task:

```
    lazy val root = (project in file(".")).
      aggregate(util, core).
      settings(
        aggregate in update := false
      )
```

> In your case, set aggregate in flywayMigrate := false instead. To do this just once,

```
sbt "; set aggregate in (ThisBuild, flywayMigrate) := false; root/flywayMigrate"
```

https://stackoverflow.com/questions/39623651/flyway-migration-multi-project-run-sbt-command-for-main-module-but-not-submo

- [ ] https://blog.rockthejvm.com/doobie/#8-putting-pieces-together-a-tagless-final-approach
- [ ] check(
      Note that query arguments are never used, so they can be any values that typecheck.
      как не писать аргументы совсем
- [ ] проверить объявления пакетов и package object
- [ ] client import plugin (sorting)
- [ ] set git hooks equals CI

INFO

- Чтобы создать любой из предоставленных Transactor[M] (кроме DriverManagerTransactor), вам нужно ContextShift[M], который предоставляет пул с привязкой к ЦП для неблокирующих операций
- Поскольку эти пулы должны быть закрыты, чтобы выйти без ошибок, обычно используется Resource для управления их жизненным циклом
- JDBC driver manager will try to load the driver for each connection - which can be pretty expensive
- driver manager has no upper bound on the number of connections it will create

DONE

- [x] приходит не понятный Json "::": Array(3) [ {…}, {…}, {…} ]
- [x] переименовать и проверить HelloWorldSpec
- [x] using the Resource type to manage the connection pool
- [x] ? use newHikariTransactor
