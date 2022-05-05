package com.example.todo

import cats.effect.Sync
import cats.implicits._
import org.http4s.HttpRoutes
import org.http4s.dsl.Http4sDsl
import com.example.todo.types._
import com.example.todo.types.TodoEnc._
import cats.effect.IO

object TodoRoutes {

  def jokeRoutes[F[_]: Sync](J: Jokes[F]): HttpRoutes[F] = {
    val dsl = new Http4sDsl[F] {}
    import dsl._
    HttpRoutes.of[F] { case GET -> Root / "joke" =>
      for {
        joke <- J.get
        resp <- Ok(joke)
      } yield resp
    }
  }

  def serverApiRoutes(api: TodoApi): HttpRoutes[IO] = {
    val dsl = new Http4sDsl[IO] {}
    import dsl._
    HttpRoutes.of[IO] { case GET -> Root / "todos" / name =>
      for {
        list <- api.todos(Name(name))
        resp <- Ok(list)
      } yield resp
    }
  }
}
