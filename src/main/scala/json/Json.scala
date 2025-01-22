//package json
//
//sealed trait Json {
//  def toJsonText(j: Json): String = j match {
//    case JsonObject(stringToJsonMap) =>
//      stringToJsonMap
//        .map { case (key, value) =>
//          s""""$key":${toJsonText(value)}"""
//        }
//        .mkString("{", ",", "}")
//    case JsonArray(elements) => elements.map(toJsonText).mkString("[", ",", "]")
//    case JsonString(value)   => s""""${value.replace("\"", "\\\"")}""""
//    case JsonNumber(value)   => value.toString
//    case JsonBoolean(value)  => value.toString
//    case JsonNull            => "null"
//  }
//}
//
//case class JsonObject(stringToJsonMap: Map[String, Json]) extends Json
//case class JsonArray(elements: Seq[Json]) extends Json
//case class JsonString(value: String) extends Json
//case class JsonNumber(value: Double) extends Json
//case class JsonBoolean(value: Boolean) extends Json
//case object JsonNull extends Json
