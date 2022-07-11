package com.example.todo.http4s

import cats.effect.Sync
import cats.implicits._
import org.http4s.HttpRoutes
import org.http4s.dsl.Http4sDsl
import com.example.todo.types.TodoEnc._
import cats.effect.IO
import com.example.todo.Tests

object TodoRoutes {

  def testRoutes[F[_]: Sync](T: Tests[F]): HttpRoutes[F] = {
    val dsl = new Http4sDsl[F] {}
    import dsl._
    HttpRoutes.of[F] { case GET -> Root / "test" =>
      for {
        test <- T.get
        resp <- Ok(test)
      } yield resp
    }
  }

  def todoRoutes(api: TodoApi): HttpRoutes[IO] = {
    val dsl = new Http4sDsl[IO] {}
    import dsl._
    HttpRoutes.of[IO] { case GET -> Root / "todos" =>
      for {
        list <- api.todos()
        resp <- Ok(list)
      } yield resp
    }
  }
}
