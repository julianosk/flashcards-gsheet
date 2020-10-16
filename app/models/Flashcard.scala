package models

import java.time.LocalDate

import play.api.libs.json.{Json, OFormat}


case class Flashcard(
                      word: String,
                      translation: String,
                      example: String,
                      level: Int,
                      last_seen: LocalDate)

object Flashcard {
  implicit val format: OFormat[Flashcard] = Json.format[Flashcard]
}
