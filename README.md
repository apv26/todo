Открытие tapir-документации API

```
http://localhost:8081/docs/#/
```

Temporary use in "repositories" to download dependency?
maven-central: https://repo1.maven.org/maven2

Проверка кода

```
> server/test:scalastyle
```

```
> server/scalastyle
```

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

run the container

```
> docker run -d -p 80:80 docker/getting-started
```

docker build command

```
> docker build -t getting-started
```

TODO

- [x] тестирование docker image postgres; интеграционный тест чтобы не аклиенте отображались нужные записи в списке (поправить тесты)
      https://docs.docker.com/ci-cd/best-practices/
      https://docs.docker.com/ci-cd/github-actions/
- [x] use flyway migrations
- [x] use scalaCheckStyle
- [x] scala test for queries
- [ ] add swagger
      https://tapir.softwaremill.com/en/v0.12.23/openapi.html
      https://tapir.softwaremill.com/en/v0.12.23/server/http4s.html
      https://tapir.softwaremill.com/en/v0.12.23/examples.html
      https://github.com/softwaremill/tapir/blob/master/examples/src/main/scala/sttp/tapir/examples/HelloWorldHttp4sServer.scala
      https://github.com/softwaremill/tapir/blob/master/examples/src/main/scala/sttp/tapir/examples/openapi/MultipleEndpointsDocumentationHttp4sServer.scala#L79

PART

- [ ] val transactor создавать в db object?
- [ ] сделать слой запросов val findAllTodosQuery: doobie.Query0[Todo]
      слой ConnectionIO - val findAllTodos: doobie.ConnectionIO[List[Todo]]
      слой Todo - IO[List[Todo]]
- [ ] add pureconfig, данные для входа
- [ x] из todos(n: Name) удалить n, delete jokeRoutes, rename serverApiRoutes
- [ ] should use ember client builder?
- [ ] кнопки добавить, удалить, не/выполнено; example of todo https://docs.docker.com/get-started/02_our_app/
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

PART2

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

PART3

- [ ] docker ci test db queries https://docs.github.com/en/actions/using-containerized-services/creating-postgresql-service-containers
- [ ] docker compose https://github.com/peter-evans/docker-compose-actions-workflow
- [ ] docker scala test
- [ ] docker yarn test
- [ ] build server with client
- [ ] docker - build server with client
- [ ] ? plugin sbt-github-actions https://github.com/djspiewak/sbt-github-actions

INFO

- Чтобы создать любой из предоставленных Transactor[M] (кроме DriverManagerTransactor), вам нужно ContextShift[M], который предоставляет пул с привязкой к ЦП для неблокирующих операций
- Поскольку эти пулы должны быть закрыты, чтобы выйти без ошибок, обычно используется Resource для управления их жизненным циклом
- JDBC driver manager will try to load the driver for each connection - which can be pretty expensive
- driver manager has no upper bound on the number of connections it will create

_docker-installation_
Docker for Windows require Windows 10. If you use an older version of Windows like Windows 8.1, you should use Docker Toolbox instead. It's a legacy solution for older windows and mac.
For that, you will need to install VirtualBox and you need to keep in mind that docker will run inside a VM. If you start and expose a container port, you can't connect on it using localhost but, instead, the VM IP.

https://tapir.softwaremill.com/en/latest/
https://tapir.softwaremill.com/en/latest/server/http4s.html
https://tapir.softwaremill.com/en/latest/endpoint/schemas.html

PFP-SCALA

- Smart constructors are functions such as mkUsername and mkEmail, which take a raw value and return an optional validated one.

- `sealed abstract class Username(value: String)`

- we can combine forces and use Refined and Newtype together! `@newtype case class Brand(value: NonEmptyString)`

DONE

- [x] приходит не понятный Json "::": Array(3) [ {…}, {…}, {…} ]
- [x] переименовать и проверить HelloWorldSpec
- [x] using the Resource type to manage the connection pool
- [x] ? use newHikariTransactor
