package json2

sealed trait Json {
  def toText: String = this match {
    case JsonObject(stringToJsonMap) =>
      stringToJsonMap
        .map { case (key, value) =>
          s""""$key":${value.toText}"""
        }
        .mkString("{", ",", "}")
    case JsonArray(seq)           => seq.map(_.toText).mkString("[", ",", "]")
    case JsonString(str)          => s""""${str.replace("\"", "\\\"")}""""
    case JsonNumberInt(int)       => int.toString
    case JsonNumberDouble(double) => double.toString
    case JsonBoolean(boolean)     => boolean.toString
    case JsonNull                 => "null"
  }
}

case class JsonObject(value: Map[String, Json]) extends Json
case class JsonArray(value: Seq[Json]) extends Json
case class JsonString(value: String) extends Json
case class JsonNumberInt(value: Int) extends Json
case class JsonNumberDouble(value: Double) extends Json
case class JsonBoolean(value: Boolean) extends Json
case object JsonNull extends Json
