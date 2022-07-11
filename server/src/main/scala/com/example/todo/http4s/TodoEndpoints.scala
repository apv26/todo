package com.example.todo.http4s

import cats.effect._
import cats.syntax.all._
import io.circe.generic.auto._
import org.http4s.HttpRoutes
import org.http4s.blaze.server.BlazeServerBuilder
import org.http4s.server.middleware.CORS
import org.http4s.server.Router
import sttp.tapir._
import sttp.tapir.generic.auto._
import sttp.tapir.json.circe._
import sttp.tapir.server.http4s.Http4sServerInterpreter
import sttp.tapir.swagger.bundle.SwaggerInterpreter

import scala.concurrent.ExecutionContext
import com.example.todo.db.Todo

object TodoEndpoints extends IOApp {

  val todoListing: PublicEndpoint[Unit, Unit, List[Todo], Any] = endpoint.get
    .in("todos")
    .out(jsonBody[List[Todo]])

  val addTodo: PublicEndpoint[Todo, Unit, Unit, Any] = endpoint.post
    .in("todos")
    .in("add")
    .in(
      jsonBody[Todo]
        .description("The todo to add")
        .example(Todo(4, "Read new article", false))
    )

  // server-side logic
  implicit val ec: ExecutionContext =
    scala.concurrent.ExecutionContext.Implicits.global

  val todosListingRoutes: HttpRoutes[IO] =
    Http4sServerInterpreter[IO]().toRoutes(
      todoListing.serverLogicSuccess(_ => TodoApi.impl.todos())
    )
  val addTodoRoutes: HttpRoutes[IO] =
    Http4sServerInterpreter[IO]().toRoutes(
      addTodo.serverLogicSuccess(todo =>
        // TODO implement API
        IO((println(todo): Unit))
      )
    )

  // generating and exposing the documentation in yml
  val swaggerUIRoutes: HttpRoutes[IO] =
    Http4sServerInterpreter[IO]().toRoutes(
      SwaggerInterpreter().fromEndpoints[IO](
        List(todoListing, addTodo),
        "The tapir library",
        "1.0.0"
      )
    )

  val routes: HttpRoutes[IO] =
    todosListingRoutes <+> addTodoRoutes <+> swaggerUIRoutes

  override def run(args: List[String]): IO[ExitCode] = {
    // starting the server
    BlazeServerBuilder[IO]
      .withExecutionContext(ec)
      .bindHttp(8081, "localhost")
      .withHttpApp(
        CORS.policy.withAllowOriginAll
          .withAllowCredentials(false)
          .apply(Router("/" -> (routes)).orNotFound)
      )
      .serve
      .compile
      .drain
      .as(ExitCode.Success)
  }
}
