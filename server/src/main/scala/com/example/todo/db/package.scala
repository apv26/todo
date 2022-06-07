package com.example.todo.db

import cats.effect._
import doobie._
import doobie.hikari.HikariTransactor

package object db {

  val THREAD_POOL_SIZE = 32

  val transactor: Resource[IO, HikariTransactor[IO]] = for {
    ce <- ExecutionContexts.fixedThreadPool[IO](THREAD_POOL_SIZE)
    xa <- HikariTransactor.newHikariTransactor[IO](
      "org.postgresql.Driver",
      "jdbc:postgresql:todo",
      "postgres",
      "1111",
      ce
    )
  } yield xa
}
