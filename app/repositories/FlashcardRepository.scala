package repositories

import java.time.LocalDate
import java.time.format.DateTimeFormatter

import gsheets.GSheetsClient
import javax.inject.{Inject, Singleton}
import models.Flashcard

import scala.collection.JavaConverters._
import scala.concurrent.ExecutionContext

trait FlashcardRepository {
  def findAll(): List[Flashcard]

  def findByRow(row: Int): Flashcard

  def review(row: Int): Boolean
}

@Singleton
class FlashcardRepositoryImpl @Inject()(gSheetsClient: GSheetsClient)(implicit ec: ExecutionContext)
  extends FlashcardRepository {

  val dateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

  override def findAll(): List[Flashcard] =
    gSheetsClient.findAll.asScala
      .drop(1) // remove table header
      .zipWithIndex
      .map {
        case (row, rowNumber) =>
          new Flashcard(
            rowNumber + 2, // first row is the header!
            row.get(0).toString,
            row.get(1).toString,
            row.get(2).toString,
            row.get(3).toString.toInt,
            toDate(row.get(4).toString)
          )
      }.toList


  override def findByRow(row: Int): Flashcard = {
    val result = gSheetsClient.findByRange(s"Sheet1!$row:$row").get(0)
    Flashcard(
      row,
      result.get(0).toString,
      result.get(1).toString,
      result.get(2).toString,
      result.get(3).toString.toInt,
      toDate(result.get(4).toString)
    )
  }

  private def toDate(value: String) = {
    LocalDate.parse(value, dateTimeFormatter)
  }

  override def review(row: Int): Boolean = {
    val values = List(
      List[Object](dateTimeFormatter.format(LocalDate.now())
      ).asJava
    ).asJava
    gSheetsClient.updateCells(range = s"E${row}:E${row}", values).equals(1)
  }

}
