name := "catdrip"

version := "0.1"

scalaVersion := "2.12.6"


libraryDependencies ++= {
  def akkaGroup = "com.typesafe.akka"

  def akkaVersion = "2.5.13"

  def akkaHttpVersion = "10.1.1"

  Seq(
    akkaGroup %% "akka-actor" % akkaVersion,
    akkaGroup %% "akka-stream" % akkaVersion,
    akkaGroup %% "akka-http" % akkaHttpVersion,
    akkaGroup %% "akka-slf4j" % akkaVersion
  )
}



libraryDependencies ++= {
  def lightbendGroup = "com.lightbend.akka"

  def alpakkaVersion = "0.19"

  Seq(
    lightbendGroup %% "akka-stream-alpakka-slick" % alpakkaVersion,
    lightbendGroup %% "akka-stream-alpakka-s3" % alpakkaVersion
  )
}

libraryDependencies ++= {
  def slickGroup = "com.typesafe.slick"

  def slickVersion = "3.2.1"

  Seq(
    slickGroup %% "slick" % slickVersion,
    slickGroup %% "slick-hikaricp" % slickVersion
  )
}

libraryDependencies += "org.jsoup" % "jsoup" % "1.11.3"

libraryDependencies += "org.slf4j" % "slf4j-simple" % "1.7.25"

resolvers += "Akka Snapshot Repository" at "https://repo.akka.io/snapshots/"
resolvers += "Java.net Maven2 Repository" at "https://download.java.net/maven/2/"
resolvers += "Maven Central" at "https://repo.maven.apache.org/maven2"
