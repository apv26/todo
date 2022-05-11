package com.example.todo

import cats.effect.IO
import org.http4s._
import org.http4s.implicits._
import munit.CatsEffectSuite

class TodosRoutesSpec extends CatsEffectSuite {

  test("Todos returns status code 200") {
    assertIO(retTodos.map(_.status), Status.Ok)
  }

  test("Todos returns list of todo") {
    assertIO(
      retTodos.flatMap(_.as[String]),
      "[" +
        "{\"id\":1,\"description\":\"buy the paper\",\"done\":true}," +
        "{\"id\":2,\"description\":\"find sample of picture\",\"done\":false}," +
        "{\"id\":3,\"description\":\"draw picture\",\"done\":false}" +
        "]"
    )
  }

  private[this] val retTodos: IO[Response[IO]] = {
    val getTodos = Request[IO](Method.GET, uri"/todos")
    val api = TodoApi.impl
    TodoRoutes.todoRoutes(api).orNotFound(getTodos)
  }
}
