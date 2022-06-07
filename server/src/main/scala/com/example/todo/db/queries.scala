package com.example.todo.db

import cats.effect._
import doobie.implicits._

object Queries {

  val findAllTodosQuery: doobie.Query0[Todo] =
    sql"select * from todo".query[Todo]

  def findAllTodos(): IO[List[Todo]] = db.transactor.use { xa =>
    {
      val findAllTodos: doobie.ConnectionIO[List[Todo]] =
        findAllTodosQuery.to[List]

      findAllTodos.transact(xa)
    }
  }
}
