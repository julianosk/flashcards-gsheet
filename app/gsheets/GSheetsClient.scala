package gsheets

import java.io.{File, FileNotFoundException, InputStreamReader}
import java.util.{Collections, List => JList}

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
import play.api.{Configuration, Environment}

import scala.concurrent.ExecutionContext

@Singleton
class GSheetsClient @Inject()(env: Environment,
                              config: Configuration
                             )(implicit ec: ExecutionContext) {

  val spreadsheetId: String = config.get[String]("gsheets.spreadsheet-id")
  val sheetName: String = config.get[String]("gsheets.sheet-name")
  val spreadsheets: Sheets#Spreadsheets = createSpreadsheetClient

  def findAll: JList[JList[AnyRef]] = findByRange(sheetName)

  def findByRange(range: String): JList[JList[AnyRef]] = {
    spreadsheets.values.get(spreadsheetId, range).execute
      .getValues
  }

  def updateCells(range: String, values: JList[JList[AnyRef]]): Integer = {
    spreadsheets.values.update(spreadsheetId, range, new ValueRange().setValues(values))
      .setValueInputOption("USER_ENTERED")
      .execute().getUpdatedCells
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
