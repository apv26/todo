package com.example.todo

import cats.effect.Concurrent
import cats.implicits._
import io.circe.{Encoder, Decoder}
import io.circe.generic.semiauto._
import org.http4s._
import org.http4s.implicits._
import org.http4s.client.Client
import org.http4s.client.dsl.Http4sClientDsl
import org.http4s.circe._
import org.http4s.Method._

trait Tests[F[_]] {
  def get: F[Tests.Test]
}

object Tests {
  def apply[F[_]](implicit ev: Tests[F]): Tests[F] = ev

  final case class Test(test: String) extends AnyVal
  object Test {
    implicit val testDecoder: Decoder[Test] = deriveDecoder[Test]
    implicit def testEntityDecoder[F[_]: Concurrent]: EntityDecoder[F, Test] =
      jsonOf
    implicit val testEncoder: Encoder[Test] = deriveEncoder[Test]
    implicit def testEntityEncoder[F[_]]: EntityEncoder[F, Test] =
      jsonEncoderOf
  }

  final case class TestError(e: Throwable) extends RuntimeException

  def impl[F[_]: Concurrent](C: Client[F]): Tests[F] = new Tests[F] {
    val dsl = new Http4sClientDsl[F] {}
    import dsl._
    def get: F[Tests.Test] = {
      C.expect[Test](GET(uri"https://icanhazdadtest.com/"))
        .adaptError { case t =>
          TestError(t)
        } // Prevent Client Json Decoding Failure Leaking
    }
  }
}
