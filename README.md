- [ ] тестирование docker image postgres
- [ ] JDBC driver manager will try to load the driver for each connection - which can be pretty expensive
- [ ] driver manager has no upper bound on the number of connections it will create
- [ ] using the Resource type to manage the connection pool
- [ ] ? use newHikariTransactor

- [ ] use flyway migrations
- [ ] use scalaCheckStyle
- [ ] scala test for queries
- [ ] add swagger
- [ ] приходит не понятный Json "::": Array(3) [ {…}, {…}, {…} ]

Pg super user - 1111

Запуск сервера
server/reStart

Тестирование рест в консоли:
curl http://localhost:8081/todos/user

`git log --pretty=format:"%h - %an, %ar : %s" --since=1.week --graph`
