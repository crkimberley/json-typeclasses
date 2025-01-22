package json3

import json3.Json.*

trait JsonDecoder[A] {
  def decode(json: Json): Either[String, A]
}

given [A](using decoder: JsonDecoder[A]): JsonDecoder[Seq[A]] with
  def decode(json: Json): Either[String, Seq[A]] = json match {
    case JsonArray(elements) =>
      elements
        .foldLeft[Either[String, Seq[A]]](Right(Seq.empty[A])) {
          case (Right(acc), element) => decoder.decode(element).map(_ +: acc)
          case (Left(error), _)      => Left(error)
        }
        .map(_.reverse)
    case _ => Left(s"$json is not a JsonArray")
  }

given JsonDecoder[String] with
  def decode(json: Json): Either[String, String] = json match {
    case JsonString(str) => Right(str)
    case _               => Left(s"$json is not a String")
  }

given JsonDecoder[Double] with
  def decode(json: Json): Either[String, Double] = json match {
    case JsonNumberDouble(number) => Right(number)
    case _                        => Left(s"$json is not a Double")
  }

given JsonDecoder[Int] with
  def decode(json: Json): Either[String, Int] = json match {
    case JsonNumberInt(number) => Right(number)
    case _                     => Left(s"$json is not an Int")
  }

given JsonDecoder[Boolean] with
  def decode(json: Json): Either[String, Boolean] = json match {
    case JsonBoolean(bool) => Right(bool)
    case _                 => Left(s"$json is not a Boolean")
  }

given JsonDecoder[None.type] with
  def decode(json: Json): Either[String, None.type] = json match {
    case JsonNull => Right(None)
    case _        => Left(s"$json is not a null")
  }

extension [T](json: Json)
  def as[A](using decoder: JsonDecoder[A]): Either[String, A] =
    decoder.decode(json)
