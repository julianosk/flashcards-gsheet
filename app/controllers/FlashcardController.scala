package controllers

import controllers.ReviewForm.Data
import javax.inject.Inject
import play.api.libs.json.Json
import play.api.mvc._
import services.FlashcardService

import scala.concurrent.{ExecutionContext, Future}

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
class FlashcardController @Inject()(cc: ControllerComponents,
                                    service: FlashcardService)
                                   (implicit ec: ExecutionContext)
  extends AbstractController(cc) {

  def findAll(): Action[AnyContent] = Action.async { implicit request: Request[AnyContent] =>
    service.next() map { items =>
      Ok(Json.toJson(items))
    }
  }

  def next(limit: Int): Action[AnyContent] = Action.async { implicit request: Request[AnyContent] =>
    service.next(limit) map { items =>
      Ok(Json.toJson(items))
    }
  }

  def get(row: Int): Action[AnyContent] = Action.async { implicit request: Request[AnyContent] =>
    service.get(row) map { item =>
      Ok(Json.toJson(item))
    }
  }

  def review(row: Int): Action[AnyContent] = Action.async { implicit request: Request[AnyContent] =>
    ReviewForm.form.bindFromRequest().fold(
      { _ => Future.successful(BadRequest) }, { data: Data =>
        service.review(row, data.level).map {
          case true => Ok
          case false => BadRequest
        }
      })
  }

}

object ReviewForm {

  import play.api.data.Form
  import play.api.data.Forms._

  case class Data(level: Int)

  val form: Form[Data] = Form(
    mapping(
      "level" -> number()
    )(Data.apply)(Data.unapply)
  )
}
