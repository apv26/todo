package com.example.db

import cats.effect._
import doobie._
import doobie.hikari.HikariTransactor

package object db {

  val transactor: Resource[IO, HikariTransactor[IO]] = for {
    ce <- ExecutionContexts.fixedThreadPool[IO](32)
    xa <- HikariTransactor.newHikariTransactor[IO](
      "org.postgresql.Driver",
      "jdbc:postgresql:todo",
      "postgres",
      "1111",
      ce
    )
  } yield xa
}
