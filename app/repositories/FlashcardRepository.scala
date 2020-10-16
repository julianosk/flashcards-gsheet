package repositories

import gsheets.GSheetsClient
import javax.inject.{Inject, Singleton}
import models.Flashcard

import scala.concurrent.{ExecutionContext, Future}

trait FlashcardRepository {
  def findAll(): Future[List[Flashcard]]
  def next(): Future[List[Flashcard]]
}

@Singleton
class FlashcardRepositoryImpl @Inject ()(gSheetsClient: GSheetsClient)(implicit ec: ExecutionContext)
  extends FlashcardRepository {

  override def findAll(): Future[List[Flashcard]] = Future {
    gSheetsClient.findAll()
  }

  override def next(): Future[List[Flashcard]] = Future {
    gSheetsClient.findAll()
      .sortBy(_.last_seen)(Ordering.by(_.toEpochDay))
      .sortBy(_.level)
      .take(10)
  }
}
