Запуск сервера
server/reStart

Тестирование рест в консоли:
curl http://localhost:8081/todos/user

`git log --pretty=format:"%h - %an, %ar : %s" --since=1.week --graph`

Pg super user - 1111

TODO

- [ ] тестирование docker image postgres; интеграционный тест чтобы не аклиенте отображались нужные записи в списке
- [ ] use flyway migrations
- [ ] use scalaCheckStyle
- [ ] scala test for queries
- [ ] add swagger
- [ ] приходит не понятный Json "::": Array(3) [ {…}, {…}, {…} ]

NEXT

- [ ] val transactor создавать в db object?
- [ ] сделать слой запросов val findAllTodosQuery: doobie.Query0[Todo]
      слой ConnectionIO - val findAllTodos: doobie.ConnectionIO[List[Todo]]
      слой Todo - IO[List[Todo]]
- [ ] add pureconfig, данные для входа
- [ ] из todos(n: Name) удалить n, delete jokeRoutes, rename serverApiRoutes
- [ ] should use ember client builder?
- [ ] переименовать и проверить HelloWorldSpec
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

INFO

- Чтобы создать любой из предоставленных Transactor[M] (кроме DriverManagerTransactor), вам нужно ContextShift[M], который предоставляет пул с привязкой к ЦП для неблокирующих операций
- Поскольку эти пулы должны быть закрыты, чтобы выйти без ошибок, обычно используется Resource для управления их жизненным циклом
- JDBC driver manager will try to load the driver for each connection - which can be pretty expensive
- driver manager has no upper bound on the number of connections it will create

DONE

- [x] using the Resource type to manage the connection pool
- [x] ? use newHikariTransactor
