package com.example.todo

import com.example.todo.types._
import com.example.db.Todo
import com.example.db.queries._
import cats.effect.IO

trait TodoApi {
  def todos(n: Name): IO[List[Todo]]
}

object TodoApi {
  implicit def apply(implicit ev: TodoApi): TodoApi = ev

  def impl: TodoApi = new TodoApi {
    def todos(n: Name): IO[List[Todo]] = {
      findAllTodos()
    }
  }
}
