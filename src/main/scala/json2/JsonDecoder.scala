package json2

trait JsonDecoder[A] {
  def decode(json: Json): Either[String, A]
}

implicit def seqDecoder[A](implicit decoder: JsonDecoder[A]): JsonDecoder[Seq[A]] = {
  case JsonArray(elements) =>
    elements
      .foldLeft[Either[String, Seq[A]]](Right(Seq.empty[A])) {
        case (Right(acc), element) => element.as[A].map(_ +: acc)
        case (Left(error), _)      => Left(error)
      }
      .map(_.reverse)
  case json => Left(s"$json is not a JsonArray")
}

implicit val stringDecoder: JsonDecoder[String] = {
  case JsonString(str) => Right(str)
  case json            => Left(s"$json is not a String")
}

implicit val doubleDecoder: JsonDecoder[Double] = {
  case JsonNumberDouble(number) => Right(number)
  case json                     => Left(s"$json is not a Double")
}

implicit val intDecoder: JsonDecoder[Int] = {
  case JsonNumberInt(number) => Right(number)
  case json                  => Left(s"$json is not an Int")
}

implicit val booleanDecoder: JsonDecoder[Boolean] = {
  case JsonBoolean(bool) => Right(bool)
  case json              => Left(s"$json is not a Boolean")
}

implicit val noneDecoder: JsonDecoder[None.type] = {
  case JsonNull => Right(None)
  case json     => Left(s"$json is not a null")
}
