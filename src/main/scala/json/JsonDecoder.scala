//package json
//
//trait JsonDecoder[A] {
//  def decode(json: Json): Either[String, A]
//}
//
//given JsonDecoder[Map[String, Json]] with
//  def decode(json: Json) = json match {
//    case JsonObject(map) => Right(map)
//    case _ => Left("")
//  }
//
//given JsonDecoder[Seq[Json]] with
//  def decode(json: Json) = json match {
//    case JsonArray(elements) => Some(elements)
//    case _ => None
//  }
//
//given JsonDecoder[String] with
//  def decode(json: Json) = json match {
//    case JsonString(value) => Some(value)
//    case _ => None
//  }
//
//given JsonDecoder[Double] with
//  def decode(json: Json) = json match {
//    case JsonNumber(value) => Some(value)
//    case _ => None
//  }
//
//given JsonDecoder[Int] with
//  def decode(json: Json) = json match {
//    case JsonNumber(value) => Some(value.toInt)
//    case _ => None
//  }
//
//given JsonDecoder[Boolean] with
//  def decode(json: Json) = json match {
//    case JsonBoolean(value) => Some(value)
//    case _ => None
//  }
//
//given JsonDecoder[Null] with
//  def decode(json: Json) = json match {
//    case JsonNull => None
//    case _ => None
//  }