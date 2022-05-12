package com.example.todo

import cats.effect.{ExitCode, IOApp}

object Main extends IOApp {
  def run(args: List[String]) =
    http4s.TodoServer.stream.compile.drain.as(ExitCode.Success)
}
