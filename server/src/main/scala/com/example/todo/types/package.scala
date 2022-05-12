package com.example.todo.types

import io.circe.{Json}
import org.http4s.{EntityEncoder, EntityDecoder}
import org.http4s.circe._
import com.example.todo.db.Todo
import io.circe.{Encoder, Decoder}
import io.circe.generic.semiauto._
import cats.effect.Concurrent

final case class Name(name: String) extends AnyVal

/** More generally you will want to decouple your edge representations from your
  * internal data structures, however this shows how you can create encoders for
  * your data.
  */
final case class Greeting(greeting: String) extends AnyVal
object Greeting {
  implicit val greetingEncoder: Encoder[Greeting] = new Encoder[Greeting] {
    final def apply(a: Greeting): Json = Json.obj(
      ("message", Json.fromString(a.greeting))
    )
  }
  implicit def greetingEntityEncoder[F[_]]: EntityEncoder[F, Greeting] =
    jsonEncoderOf[F, Greeting]
}

/** More generally you will want to decouple your edge representations from your
  * internal data structures, however this shows how you can create encoders for
  * your data.
  */
object TodoEnc {
  implicit val todoDecoder: Decoder[Todo] = deriveDecoder[Todo]
  implicit def todoEntityDecoder[F[_]: Concurrent]: EntityDecoder[F, Todo] =
    jsonOf

  implicit val todoEncoder: Encoder[Todo] = deriveEncoder[Todo]
  implicit def todoEntityEncoder[F[_]]: EntityEncoder[F, Todo] =
    jsonEncoderOf

  implicit def todoListEntityEncoder[F[_]]: EntityEncoder[F, List[Todo]] =
    jsonEncoderOf
}
