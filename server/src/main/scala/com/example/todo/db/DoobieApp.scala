package com.example.todo.db

import cats.effect._
import doobie._
import doobie.implicits._
import com.example.db.Actor
// Very important to deal with arrays

object DoobieApp extends IOApp {

  val xa: Transactor[IO] = Transactor.fromDriverManager[IO](
    "org.postgresql.Driver", // driver classname
    "jdbc:postgresql:todo", // connect URL (driver-specific)
    "postgres", // user
    "1111" // password
  )

  def findAllActorsNamesProgram(): IO[List[String]] = {
    val findAllActorsQuery: doobie.Query0[String] =
      sql"select name from actors".query[String]
    val findAllActors: doobie.ConnectionIO[List[String]] =
      findAllActorsQuery.to[List]
    findAllActors.transact(xa)
  }

  override def run(args: List[String]): IO[ExitCode] = {
    findAllActorsNamesProgram()
      .map(println)
      .as(ExitCode.Success)
  }

  def findActorByIdProgram(id: Int): IO[Option[Actor]] = {
    val findActorById: doobie.ConnectionIO[Option[Actor]] =
      sql"select id, name from actors where id = $id".query[Actor].option
    findActorById.transact(xa)
  }

  val actorsNamesStream: fs2.Stream[doobie.ConnectionIO, String] =
    sql"select name from actors".query[String].stream

  val actorsNamesList: IO[List[String]] =
    actorsNamesStream.compile.toList.transact(xa)

  def findAllActorsIdsAndNamesProgram: IO[List[(Int, String)]] = {
    val query: doobie.Query0[(Int, String)] =
      sql"select id, name from actors".query[(Int, String)]
    val findAllActors: doobie.ConnectionIO[List[(Int, String)]] = query.to[List]
    findAllActors.transact(xa)
  }

  def findAllActorsProgram: IO[List[Actor]] = {
    val findAllActors: fs2.Stream[doobie.ConnectionIO, Actor] =
      sql"select id, name from actors".query[Actor].stream
    findAllActors.compile.toList.transact(xa)
  }

  def findActorsByNameInitialLetterProgram(
      initialLetter: String
  ): IO[List[Actor]] = {
    val findActors: fs2.Stream[doobie.ConnectionIO, Actor] =
      sql"select id, name from actors where LEFT(name, 1) = $initialLetter"
        .query[Actor]
        .stream
    findActors.compile.toList.transact(xa)
  }
}
