package json2

trait JsonEncoders[A] {
  def encode(value: A): Json
}

object JsonEncoders {
  implicit def seqEncoder[A](implicit encoder: JsonEncoders[A]): JsonEncoders[Seq[A]] =
    (value: Seq[A]) => JsonArray(value.map(_.toJson))

  implicit val stringEncoder: JsonEncoders[String] =
    (value: String) => JsonString(value)

  implicit val doubleEncoder: JsonEncoders[Double] =
    (value: Double) => JsonNumberDouble(value)

  implicit val intEncoder: JsonEncoders[Int] =
    (value: Int) => JsonNumberInt(value)

  implicit val booleanEncoder: JsonEncoders[Boolean] =
    (value: Boolean) => JsonBoolean(value)

  implicit def someEncoder[A](implicit encoder: JsonEncoders[A]): JsonEncoders[Some[A]] =
    (value: Some[A]) => encoder.encode(value.get)

  implicit val noneEncoder: JsonEncoders[None.type] =
    (value: None.type) => JsonNull

  implicit def optionEncoder[A](implicit encoder: JsonEncoders[A]): JsonEncoders[Option[A]] = {
    case Some(v) => encoder.encode(v)
    case None    => JsonNull
  }
}
