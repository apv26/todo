package com.example.todo.db

import org.specs2.mutable.Specification
import cats.effect.IO
import doobie.util.transactor._

class queriesTestSpec extends Specification with doobie.specs2.IOChecker {

  val host = System.getenv("POSTGRES_HOST");
  val port = System.getenv("POSTGRES_PORT");
  val transactor = Transactor.fromDriverManager[IO](
    "org.postgresql.Driver",
    s"jdbc:postgresql://${host}:${port}/todo-test",
    "postgres",
    "1111"
  )

  check(queries.findAllTodosQuery)
}
