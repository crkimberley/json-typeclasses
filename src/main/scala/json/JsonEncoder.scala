//package json
//
//trait JsonEncoder[A] {
//  def encode(value: A): Json
//}
//
//given JsonEncoder[Map[String, Json]] with
//  def encode(value: Map[String, Json]) = JsonObject(value)
//
//given JsonEncoder[Seq[Json]] with
//  def encode(value: Seq[Json]) = JsonArray(value)
//
//given JsonEncoder[String] with
//  def encode(value: String) = JsonString(value)
//
//given JsonEncoder[Double] with
//  def encode(value: Double) = JsonNumber(value)
//
//given JsonEncoder[Int] with
//  def encode(value: Int) = JsonNumber(value.toDouble)
//
//given [A](using encoder: JsonEncoder[A]): JsonEncoder[Option[A]] with
//  def encode(value: Option[A]): Json = value match
//    case Some(a) => encoder.encode(a)
//    case None => JsonNull
