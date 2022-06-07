package com.example.todo

import cats.effect.{ExitCode, IOApp}
import cats.effect.IO

object Main extends IOApp {
  def run(args: List[String]): IO[ExitCode] =
    http4s.TodoServer.stream.compile.drain.as(ExitCode.Success)
}
