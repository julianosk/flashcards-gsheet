package repositories

import gsheets.GSheetsClient
import javax.inject.{Inject, Singleton}
import models.Flashcard

import scala.concurrent.{ExecutionContext, Future}

trait FlashcardRepository {
  def findAll(): Future[List[Flashcard]]
  def next(): Future[List[Flashcard]]
  def view(row: Int): Future[Boolean]
}

@Singleton
class FlashcardRepositoryImpl @Inject ()(gSheetsClient: GSheetsClient)(implicit ec: ExecutionContext)
  extends FlashcardRepository {

  override def findAll(): Future[List[Flashcard]] = Future {
    gSheetsClient.getAll()
  }

  override def next(): Future[List[Flashcard]] = Future {
    gSheetsClient.getAll()
      .sortBy(_.last_seen)(Ordering.by(_.toEpochDay))
      .sortBy(_.level)
      .take(10)
  }

  override def view(row: Int): Future[Boolean] = Future {
    gSheetsClient.updateLastSeenCell(row)
  }
}
