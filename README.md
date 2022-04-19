Тестирование рест в консоли:
curl http://localhost:8081/hello/user

- [ ] тестирование docker image postgres
- [ ] JDBC driver manager will try to load the driver for each connection - which can be pretty expensive
- [ ] driver manager has no upper bound on the number of connections it will create
- [ ] using the Resource type to manage the connection pool
- [ ] ? use newHikariTransactor
