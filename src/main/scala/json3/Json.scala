package json3

enum Json {
  case JsonObject(value: Map[String, Json])
  case JsonArray(value: Seq[Json])
  case JsonString(value: String)
  case JsonNumberDouble(value: Double)
  case JsonNumberInt(value: Int)
  case JsonBoolean(value: Boolean)
  case JsonNull

  def toText: String = this match {
    case JsonObject(stringToJsonMap) =>
      stringToJsonMap
        .map { case (key, value) =>
          s""""$key":${value.toText}"""
        }
        .mkString("{", ",", "}")
    case JsonArray(seq)           => seq.map(_.toText).mkString("[", ",", "]")
    case JsonString(str)          => s""""${str.replace("\"", "\\\"")}""""
    case JsonNumberDouble(number) => number.toString
    case JsonNumberInt(number)    => number.toString
    case JsonBoolean(boolean)     => boolean.toString
    case JsonNull                 => "null"
  }
}
