package com.example.todo

import cats.Applicative
import cats.implicits._
// TODO delete duplicate imports
import com.example.db.db.connect
import com.example.todo.types._

trait ServerApi[F[_]] {
  def hello(n: Name): F[Greeting]
}

object ServerApi {
  implicit def apply[F[_]](implicit ev: ServerApi[F]): ServerApi[F] = ev

  def impl[F[_]: Applicative]: ServerApi[F] = new ServerApi[F] {
    def hello(n: Name): F[Greeting] = {
      connect()
      Greeting("Hello, " + n.name).pure[F]
    }
  }
}
