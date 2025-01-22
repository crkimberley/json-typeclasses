package json3

import json3.Json.{JsonArray, JsonBoolean, JsonNull, JsonNumberDouble, JsonNumberInt, JsonObject, JsonString}

trait JsonEncoder[A] {
  def encode(value: A): Json
}

given [A](using encoder: JsonEncoder[A]): JsonEncoder[Seq[A]] with
  def encode(value: Seq[A]): JsonArray = JsonArray(value.map(toJson))

given JsonEncoder[String] with
  def encode(value: String): JsonString = JsonString(value)

given JsonEncoder[Double] with
  def encode(value: Double): JsonNumberDouble = JsonNumberDouble(value)

given JsonEncoder[Int] with
  def encode(value: Int): JsonNumberInt = JsonNumberInt(value)

given JsonEncoder[Boolean] with
  def encode(value: Boolean): JsonBoolean = JsonBoolean(value)

given [A](using encoder: JsonEncoder[A]): JsonEncoder[Some[A]] with
  def encode(value: Some[A]): Json = encoder.encode(value.get)

given JsonEncoder[None.type] with
  def encode(value: None.type): JsonNull.type = JsonNull

given [A](using encoder: JsonEncoder[A]): JsonEncoder[Option[A]] with
  def encode(value: Option[A]): Json = value match {
    case Some(v) => encoder.encode(v)
    case None    => JsonNull
  }

extension [A](value: A)
  def toJson(using encoder: JsonEncoder[A]): Json =
    encoder.encode(value)

// 2nd method using pattern matching, to allow simple Maps to be directly encoded
// rather than having to use Map[String, Json]

type JsonValueBasic = String | Int | Double | Boolean | None.type
type JsonValue = JsonValueBasic | Seq[?] | Map[String, ?]

extension (value: Any) {
  def toJsonX: Json = value match {
    case opt: Option[?] =>
      opt match {
        case Some(v) => v.toJsonX
        case None    => JsonNull
      }
    case jsonValue: JsonValue =>
      jsonValue match {
        case m: Map[String, ?] => JsonObject(m.view.mapValues(_.toJsonX).toMap)
        case seq: Seq[?]       => JsonArray(seq.map(_.toJsonX))
        case basic: JsonValueBasic =>
          basic match
            case s: String  => JsonString(s)
            case d: Double  => JsonNumberDouble(d)
            case i: Int     => JsonNumberInt(i)
            case b: Boolean => JsonBoolean(b)
            case _          => JsonNull
      }
    case _ => JsonNull
  }
}
