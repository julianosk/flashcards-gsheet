name := "flashcards-gsheets"
 
version := "1.0" 
      
lazy val `flashcards-gsheets` = (project in file(".")).enablePlugins(PlayScala)

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"
      
resolvers += "Akka Snapshot Repository" at "https://repo.akka.io/snapshots/"
      
scalaVersion := "2.12.2"

libraryDependencies ++= Seq( jdbc , ehcache , ws , specs2 % Test , guice )

// https://mvnrepository.com/artifact/com.google.api-client/google-api-client
libraryDependencies += "com.google.api-client" % "google-api-client" % "1.30.10"
// https://mvnrepository.com/artifact/com.google.oauth-client/google-oauth-client-jetty
libraryDependencies += "com.google.oauth-client" % "google-oauth-client-jetty" % "1.30.6"
// https://mvnrepository.com/artifact/com.google.apis/google-api-services-sheets
libraryDependencies += "com.google.apis" % "google-api-services-sheets" % "v4-rev612-1.25.0"

unmanagedResourceDirectories in Test += baseDirectory ( _ /"target/web/public/test" ).value
