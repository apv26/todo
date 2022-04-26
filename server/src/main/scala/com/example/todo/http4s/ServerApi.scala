package com.example.todo

import com.example.todo.types._
import com.example.db.Todo
import com.example.db.db._
import cats.effect.IO

trait ServerApi {
  def todos(n: Name): IO[List[Todo]]
}

object ServerApi {
  implicit def apply(implicit ev: ServerApi): ServerApi = ev

  def impl: ServerApi = new ServerApi {
    def todos(n: Name): IO[List[Todo]] = {
      findAllTodosProgram()
    }
  }
}
