package object json2 {
  implicit class JsonUtils[A](value: A) {
    def toJson(implicit encoder: JsonEncoder[A]): Json = encoder.encode(value)
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
}
