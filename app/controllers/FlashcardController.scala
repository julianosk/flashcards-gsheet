package controllers

import javax.inject.Inject
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents, Request}
import repositories.FlashcardRepository

import scala.concurrent.ExecutionContext

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
class FlashcardController @Inject()(cc: ControllerComponents,
                                    repository: FlashcardRepository)
                                   (implicit ec: ExecutionContext)
  extends AbstractController(cc) {

  def findAll(): Action[AnyContent] = Action.async { implicit request: Request[AnyContent] =>
    repository.findAll map { items =>
      Ok(Json.toJson(items))
    }
  }

}
