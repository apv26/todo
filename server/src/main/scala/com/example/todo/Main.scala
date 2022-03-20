package com.example.todo

import cats.effect.{ExitCode, IO, IOApp}

object Main extends IOApp {
  def run(args: List[String]) =
    TodoServer.stream[IO].compile.drain.as(ExitCode.Success)
}
