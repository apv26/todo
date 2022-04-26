package com.example.db

import cats.effect._, cats.implicits._
import doobie._, doobie.implicits._
import doobie.hikari.HikariTransactor

// TODO This is just for testing. Consider using cats.effect.IOApp instead of calling
// unsafe methods directly.
import cats.effect.unsafe.implicits.global

// TODO A transactor that gets connections from java.sql.DriverManager and executes blocking operations
// on an our synchronous EC. See the chapter on connection handling for more info.
package object db {

  val xa = Transactor.fromDriverManager[IO](
    "org.postgresql.Driver", // driver classname
    "jdbc:postgresql:todo", // connect URL (driver-specific)
    "postgres", // user
    "1111" // password
  )

  def connect() = {
    val program1 = sql"select 42".query[Int].unique
    val io = program1.transact(xa)
    // TODO create handler unsafeRunSync With
    io.handleError(e => e.printStackTrace()).unsafeRunSync()

    val interpreter = KleisliInterpreter[IO].ConnectionInterpreter
    val kleisli = program1.foldMap(interpreter)
    val io3 = IO(null: java.sql.Connection) >>= kleisli.run
    io3.unsafeRunSync()
  }

  val postgres: Resource[IO, HikariTransactor[IO]] = for {
    ce <- ExecutionContexts.fixedThreadPool[IO](32)
    xa <- HikariTransactor.newHikariTransactor[IO](
      "org.postgresql.Driver",
      "jdbc:postgresql:myimdb",
      "postgres",
      "example", // The password
      ce
    )
  } yield xa

  def findAllActorsNamesProgram(): IO[List[String]] = {
    val findAllActorsQuery: doobie.Query0[String] =
      sql"select name from actors".query[String]
    val findAllActors: doobie.ConnectionIO[List[String]] =
      findAllActorsQuery.to[List]
    findAllActors.transact(xa)
  }

  def findAllTodosProgram(): IO[List[Todo]] = {
    val findAllTodosQuery: doobie.Query0[Todo] =
      sql"select * from todos".query[Todo]
    val findAllTodos: doobie.ConnectionIO[List[Todo]] =
      findAllTodosQuery.to[List]
    findAllTodos.transact(xa)
  }
}
