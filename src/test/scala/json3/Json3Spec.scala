package json3

import com.typesafe.scalalogging.LazyLogging
import json3.Json.{JsonArray, JsonBoolean, JsonNull, JsonNumberDouble, JsonNumberInt, JsonObject, JsonString}
import org.scalatest.flatspec.AnyFlatSpec

class Json3Spec extends AnyFlatSpec with LazyLogging {

  val map1 = Map(
    "name" -> "Bob",
    "age" -> 25,
    "canDrive" -> true,
    "numbers" -> Seq(1, 4, 7),
    "aMap" -> Map("a" -> 13, "b" -> "27"),
    "seqOfMaps" -> Seq(Map("c" -> 113, "d" -> "17"), Map("e" -> 1113, "f" -> Seq("x", "y", "z")))
  )

  val map1AsJsonObject: Json = JsonObject(
    Map(
      "name" -> "Bob".toJson,
      "age" -> 25.toJson,
      "canDrive" -> true.toJson,
      "numbers" -> Seq(1, 4, 7).toJson,
      "aMap" -> JsonObject(Map("a" -> 13.toJson, "b" -> "27".toJson)),
      "seqOfMaps" -> JsonArray(
        Seq(
          JsonObject(Map("c" -> 113.toJson, "d" -> "17".toJson)),
          JsonObject(Map("e" -> 1113.toJson, "f" -> Seq("x", "y", "z").toJson))
        )
      )
    )
  )

  "JsonEncoder" should "encode all JSON types" in {
    assert("hey".toJson == JsonString("hey"))
    assert(13.toJson == JsonNumberInt(13))
    assert(1.3.toJson == JsonNumberDouble(1.3))
    assert(true.toJson == JsonBoolean(true))
    assert(Some("thing").toJson == JsonString("thing"))
    assert(None.toJson == JsonNull)
    assert((Some(42): Option[Int]).toJson == JsonNumberInt(42))
    assert(Seq(1, 2, 5).toJson == JsonArray(Seq(JsonNumberInt(1), JsonNumberInt(2), JsonNumberInt(5))))

    assert("hey".toJsonX == JsonString("hey"))
    assert(13.toJsonX == JsonNumberInt(13))
    assert(1.3.toJsonX == JsonNumberDouble(1.3))
    assert(true.toJsonX == JsonBoolean(true))
    assert(Some("thing").toJsonX == JsonString("thing"))
    assert(None.toJsonX == JsonNull)
    assert((Some(42): Option[Int]).toJsonX == JsonNumberInt(42))
    assert(Seq(1, 2, 5).toJsonX == JsonArray(Seq(JsonNumberInt(1), JsonNumberInt(2), JsonNumberInt(5))))

    assert(map1.toJsonX == map1AsJsonObject)
  }

  "Json" should "offer a correct text representation" in {

    val array1 = JsonArray(Seq(JsonNumberInt(1), JsonNumberInt(4), JsonNumberInt(7)))
    val string1 = JsonString("Some text")
    val int1 = JsonNumberInt(13)
    val double1 = JsonNumberDouble(1.3)
    val boolean1 = JsonBoolean(true)

    logger.info(toJsonText(map1AsJsonObject))
    val expectedObjectString = """{"name":"Bob","canDrive":true,"age":25,"numbers":[1,4,7],""" +
      """"seqOfMaps":[{"c":113,"d":"17"},{"e":1113,"f":["x","y","z"]}],""" +
      """"aMap":{"a":13,"b":"27"}}"""
    assert(toJsonText(map1AsJsonObject) == expectedObjectString)
    assert(toJsonText(map1AsJsonObject) == map1AsJsonObject.toText)

    logger.info(toJsonText(array1))
    assert(toJsonText(array1) == "[1,4,7]")
    assert(toJsonText(array1) == array1.toText)

    logger.info(toJsonText(string1))
    assert(toJsonText(string1) == """"Some text"""")
    assert(toJsonText(string1) == string1.toText)

    logger.info(toJsonText(int1))
    assert(toJsonText(int1) == "13")
    assert(toJsonText(int1) == int1.toText)

    logger.info(toJsonText(double1))
    assert(toJsonText(double1) == "1.3")
    assert(toJsonText(double1) == double1.toText)

    logger.info(toJsonText(boolean1))
    assert(toJsonText(boolean1) == "true")
    assert(toJsonText(boolean1) == boolean1.toText)

    logger.info(toJsonText(JsonNull))
    assert(toJsonText(JsonNull) == "null")
    assert(toJsonText(JsonNull) == JsonNull.toText)
  }

  "JsonDecoder" should "decode all JSON types" in {
    assert(JsonString("hey").as[String] == Right("hey"))
    assert(JsonNumberInt(13).as[Int] == Right(13))

  }

  "JsonDecoder" should "provide error messages when incorrect types are requested" in {
    assert(JsonString("13").as[Int] == Left("JsonString(13) is not an Int"))
    assert(JsonNumberInt(13).as[String] == Left("JsonNumberInt(13) is not a String"))
  }
}
