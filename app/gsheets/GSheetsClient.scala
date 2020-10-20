package gsheets

import java.io.{File, FileNotFoundException, InputStreamReader}
import java.time.LocalDate
import java.time.format.DateTimeFormatter
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

import scala.collection.JavaConverters._
import scala.concurrent.ExecutionContext

trait GSheetsClient {
  def getAll(): List[Flashcard]

  def updateLastSeenCell(row: Int): Boolean
}

@Singleton
class GSheetsClientImpl @Inject()(env: Environment,
                                  config: Configuration
                                 )(implicit ec: ExecutionContext)
  extends GSheetsClient {

  val spreadsheetId: String = config.get[String]("gsheets.spreadsheet-id")
  val spreadsheets: Sheets#Spreadsheets = createSpreadsheetClient
  val dateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

  override def getAll(): List[Flashcard] = {
    spreadsheets.values.get(spreadsheetId, "Sheet1").execute
      .getValues
      .asScala
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
            LocalDate.parse(row.get(4).toString, dateTimeFormatter)
          )
      }.toList
  }

  override def updateLastSeenCell(row: Int): Boolean = {
    val valueRange = new ValueRange().setValues(List(
      List[Object](dateTimeFormatter.format(LocalDate.now())
      ).asJava
    ).asJava)
    spreadsheets.values.update(spreadsheetId, s"E${row}:E${row}", valueRange)
      .setValueInputOption("USER_ENTERED")
      .execute().getUpdatedCells.equals(1)
  }

  private def createSpreadsheetClient = {
    val JSON_FACTORY = JacksonFactory.getDefaultInstance
    val HTTP_TRANSPORT: NetHttpTransport = GoogleNetHttpTransport.newTrustedTransport
    val credentialsFile = env.classLoader.getResourceAsStream("credentials.json")
    if (credentialsFile == null) throw new FileNotFoundException("Credentials file not found.")
    val credential = new AuthorizationCodeInstalledApp(
      new GoogleAuthorizationCodeFlow.Builder(
        HTTP_TRANSPORT,
        JSON_FACTORY,
        GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(credentialsFile)),
        Collections.singletonList(SheetsScopes.SPREADSHEETS)
      ).setDataStoreFactory(new FileDataStoreFactory(new File("tokens"))).setAccessType("offline").build,
      new LocalServerReceiver.Builder().setPort(8888).build)
      .authorize("user")
    new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
      .setApplicationName(config.get[String]("gsheets.app-name")).build
      .spreadsheets()
  }
}
