package models

import play.api.libs.json.{Json, OFormat}

case class Flashcard(word: String, translation: String)

object Flashcard {
  implicit val format: OFormat[Flashcard] = Json.format[Flashcard]
}
