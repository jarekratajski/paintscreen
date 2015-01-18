import sbt.{TestFrameworks, Tests}

name := "paintServer"

version := "1.0"

scalaVersion := "2.11.4"

resolvers ++= Seq(
  "typesafe.com" at "http://repo.typesafe.com/typesafe/repo/",
  "sonatype.org" at "https://oss.sonatype.org/content/repositories/releases",
  "snapshot.sonatype.org" at "https://oss.sonatype.org/content/repositories/snapshots",
  "spray.io" at "http://repo.spray.io",
 "Scalaz Bintray Repo" at "http://dl.bintray.com/scalaz/releases",
  "spray nightlies" at "http://nightlies.spray.io"
)


libraryDependencies ++= {
  val akkaV = "2.3.8"
  val sprayV = "1.3.2"
  Seq(
    "com.typesafe.akka" % "akka-actor_2.11" % "2.3.6",
    "com.typesafe.akka" %% "akka-slf4j" % "2.3.6",
    "ch.qos.logback" % "logback-classic" % "1.0.13",
    "io.spray" %% "spray-can" % sprayV,
    "io.spray" %% "spray-routing" % sprayV,
    "io.spray" %% "spray-client" % sprayV,
    "org.json4s" %% "json4s-native" % "3.2.11",
    "pl.setblack" % "airomem-core" % "1.0",
    "com.propensive" %% "rapture-json-json4s" % "1.0.8",
    "org.specs2" %% "specs2" % "2.4.11" % "test",
    "io.spray" %% "spray-testkit" % sprayV % "test" exclude("com.typesafe.akka", "akka-actor_2.10") exclude("com.typesafe.akka", "akka-testkit_2.10"),
    "com.typesafe.akka" % "akka-testkit_2.11" % "2.3.6" % "test",
    "com.novocode" % "junit-interface" % "0.7" % "test->default",
    "org.scalatest" %% "scalatest" % "2.2.1" % "test",
      "io.spray" %% "spray-json" % "1.3.0"
  )
}

scalacOptions ++= Seq(
  "-unchecked",
  "-deprecation",
  "-Xlint",
  "-Ywarn-dead-code",
  "-language:_",
  "-target:jvm-1.8",
  "-encoding", "UTF-8"
)

testOptions += Tests.Argument(TestFrameworks.JUnit, "-v")

