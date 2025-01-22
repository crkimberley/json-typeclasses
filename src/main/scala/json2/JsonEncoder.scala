package json2

trait JsonEncoder[A] {
  def encode(value: A): Json
}

object JsonEncoder {
  implicit def seqEncoder[A](implicit encoder: JsonEncoder[A]): JsonEncoder[Seq[A]] =
    (value: Seq[A]) => JsonArray(value.map(_.toJson))

  implicit val stringEncoder: JsonEncoder[String] =
    (value: String) => JsonString(value)

  implicit val doubleEncoder: JsonEncoder[Double] =
    (value: Double) => JsonNumberDouble(value)

  implicit val intEncoder: JsonEncoder[Int] =
    (value: Int) => JsonNumberInt(value)

  implicit val booleanEncoder: JsonEncoder[Boolean] =
    (value: Boolean) => JsonBoolean(value)

  implicit def someEncoder[A](implicit encoder: JsonEncoder[A]): JsonEncoder[Some[A]] =
    (value: Some[A]) => encoder.encode(value.get)

  implicit val noneEncoder: JsonEncoder[None.type] =
    (value: None.type) => JsonNull

  implicit def optionEncoder[A](implicit encoder: JsonEncoder[A]): JsonEncoder[Option[A]] = {
    case Some(v) => encoder.encode(v)
    case None    => JsonNull
  }
}
