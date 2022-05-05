package com.example.db

import cats.effect._
import doobie.implicits._

object queries {

  def findAllTodos(): IO[List[Todo]] = db.transactor.use { xa =>
    {
      val findAllTodosQuery: doobie.Query0[Todo] =
        sql"select * from todo".query[Todo]

      val findAllTodos: doobie.ConnectionIO[List[Todo]] =
        findAllTodosQuery.to[List]

      findAllTodos.transact(xa)
    }
  }
}
