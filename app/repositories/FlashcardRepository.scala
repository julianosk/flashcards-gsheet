package repositories

import gsheets.GSheetsClient
import javax.inject.{Inject, Singleton}
import models.Flashcard

import scala.concurrent.{ExecutionContext, Future}

trait FlashcardRepository {
  def findAll(): Future[Iterable[Flashcard]]
}

@Singleton
class FlashcardRepositoryImpl @Inject ()(gSheetsClient: GSheetsClient)(implicit ec: ExecutionContext)
  extends FlashcardRepository {

  private val flashcards = Seq(
    Flashcard("word1", "translation1"),
    Flashcard("word2", "translation2"),
  )

  override def findAll(): Future[Iterable[Flashcard]] = Future {
    gSheetsClient.findAll()
    flashcards
  }

}
