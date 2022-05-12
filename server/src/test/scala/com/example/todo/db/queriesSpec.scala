package com.example.todo.db

import org.specs2.mutable.Specification
import cats.effect.IO
import doobie.util.transactor._

class queriesTestSpec extends Specification with doobie.specs2.IOChecker {

  val transactor = Transactor.fromDriverManager[IO](
    "org.postgresql.Driver",
    "jdbc:postgresql:todo-test",
    "postgres",
    "1111"
  )

  check(queries.findAllTodosQuery)
}
