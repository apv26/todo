package com.example.todo

import cats.effect.{Async, Resource}
import cats.syntax.all._
import com.comcast.ip4s._
import fs2.Stream
import org.http4s.ember.client.EmberClientBuilder
import org.http4s.ember.server.EmberServerBuilder
import org.http4s.implicits._
import org.http4s.server.middleware.Logger
import cats.effect.IO
import org.http4s.server.middleware.CORS

object TodoServer {

  def stream: Stream[IO, Nothing] = {
    for {
      client <- Stream.resource(EmberClientBuilder.default[IO].build)
      serverApiAlg = ServerApi.impl
      // TODO should use ember client builder?
      jokeAlg = Jokes.impl[IO](client)

      // Combine Service Routes into an HttpApp.
      // Can also be done via a Router if you
      // want to extract a segments not checked
      // in the underlying routes.
      httpApp = CORS.policy.withAllowOriginAll
        .withAllowCredentials(false)
        .apply(
          (
            TodoRoutes.serverApiRoutes(serverApiAlg) <+>
              TodoRoutes.jokeRoutes[IO](jokeAlg)
          ).orNotFound
        )

      // With Middlewares in place
      finalHttpApp = Logger.httpApp(true, true)(httpApp)

      exitCode <- Stream.resource(
        EmberServerBuilder
          .default[IO]
          .withHost(ipv4"0.0.0.0")
          .withPort(port"8081")
          .withHttpApp(finalHttpApp)
          .build >>
          Resource.eval(Async[IO].never)
      )
    } yield exitCode
  }.drain
}
