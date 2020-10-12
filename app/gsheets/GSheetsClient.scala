package gsheets

import java.io.{File, FileNotFoundException, InputStreamReader}
import java.util
import java.util.Collections

import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver
import com.google.api.client.googleapis.auth.oauth2.{GoogleAuthorizationCodeFlow, GoogleClientSecrets}
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.client.util.store.FileDataStoreFactory
import com.google.api.services.sheets.v4.model.ValueRange
import com.google.api.services.sheets.v4.{Sheets, SheetsScopes}
import javax.inject.{Inject, Singleton}
import models.Flashcard
import play.api.{Configuration, Environment}

import scala.concurrent.{ExecutionContext, Future}

trait GSheetsClient {
  def findAll(): Future[Iterable[Flashcard]]
}

@Singleton
class GSheetsClientImpl @Inject()(env: Environment,
                              config: Configuration
                             )(implicit ec: ExecutionContext)
  extends GSheetsClient {
  private val JSON_FACTORY = JacksonFactory.getDefaultInstance
  private val SCOPES: util.List[String] = Collections.singletonList(SheetsScopes.SPREADSHEETS_READONLY)
  private val HTTP_TRANSPORT: NetHttpTransport = GoogleNetHttpTransport.newTrustedTransport
  private val credentialsFile = env.classLoader.getResourceAsStream("credentials.json")
  if (credentialsFile == null) throw new FileNotFoundException("Credentials file not found.")
  private val clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(credentialsFile))
  private val flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES).setDataStoreFactory(new FileDataStoreFactory(new File("tokens"))).setAccessType("offline").build
  private val receiver = new LocalServerReceiver.Builder().setPort(8888).build
  private val credential = new AuthorizationCodeInstalledApp(flow, receiver).authorize("user")
  val service: Sheets = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential).setApplicationName(config.get[String]("gsheets.app-name")).build
  val range = "A1:A2"

  override def findAll(): Future[Iterable[Flashcard]] = {

    val response: ValueRange = service.spreadsheets.values.get(config.get[String]("gsheets.spreadsheet-id"), range).execute
    val values = response.getValues
    Future {
      List[Flashcard]()
    }

  }
}
