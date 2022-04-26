package com.example.todo

import cats.effect.{ExitCode, IOApp}

object Main extends IOApp {
  def run(args: List[String]) =
    TodoServer.stream.compile.drain.as(ExitCode.Success)
}
