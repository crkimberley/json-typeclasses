package object json2 {
  implicit class JsonUtil1[A](value: A) {
    def toJson(implicit encoder: JsonEncoders[A]): Json = encoder.encode(value)
  }

  def toJsonText(j: Json): String = j match {
    case JsonObject(stringToJsonMap) =>
      stringToJsonMap
        .map { case (key, value) =>
          s""""$key":${toJsonText(value)}"""
        }
        .mkString("{", ",", "}")
    case JsonArray(seq)           => seq.map(toJsonText).mkString("[", ",", "]")
    case JsonString(str)          => s""""${str.replace("\"", "\\\"")}""""
    case JsonNumberDouble(number) => number.toString
    case JsonNumberInt(number)    => number.toString
    case JsonBoolean(boolean)     => boolean.toString
    case JsonNull                 => "null"
  }

  implicit class JsonUtil2(json: Json) {
    def as[A](implicit decoder: JsonDecoder[A]): Either[String, A] =
      decoder.decode(json)
  }
}
