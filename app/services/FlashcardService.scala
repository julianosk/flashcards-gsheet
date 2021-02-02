package services

import javax.inject.Inject
import models.Flashcard
import repositories.FlashcardRepository

import scala.concurrent.{ExecutionContext, Future}

class FlashcardService @Inject()(repository: FlashcardRepository)(implicit ec: ExecutionContext) {

  def next(): Future[List[Flashcard]] = next(10)

  def next(limit: Int): Future[List[Flashcard]] = Future {
    repository.findAll()
      .sortBy(_.last_seen)(Ordering.by(_.toEpochDay))
      .sortBy(_.level)
      .take(limit)
  }

  def get(row: Int): Future[Flashcard] = Future {
    repository.findByRow(row)
  }

  def review(row: Int, answer: Int): Future[Boolean] = Future {
    repository.review(row)
  }

}
