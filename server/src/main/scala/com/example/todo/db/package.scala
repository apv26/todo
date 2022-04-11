package com.example.db

import cats.effect._, cats.implicits._
import doobie._, doobie.implicits._

// TODO This is just for testing. Consider using cats.effect.IOApp instead of calling
// unsafe methods directly.
import cats.effect.unsafe.implicits.global

// TODO A transactor that gets connections from java.sql.DriverManager and executes blocking operations
// on an our synchronous EC. See the chapter on connection handling for more info.
package object db {
  val xa = Transactor.fromDriverManager[IO](
    "org.postgresql.Driver", // driver classname
    "jdbc:postgresql:world", // connect URL (driver-specific)
    "postgres", // user
    "" // password
  )

  val program1 = 42.pure[ConnectionIO]
  val io = program1.transact(xa)
  io.unsafeRunSync()
}
