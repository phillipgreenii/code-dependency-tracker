import sbt._
import Keys._

object B extends Build {
  lazy val root =
    Project("root", file("."))
      .configs(IntegrationTest)
      .settings(Defaults.itSettings: _*)
      .settings(libraryDependencies += specs)

  lazy val specs = "org.scalatest" % "scalatest_2.10" % "1.9.2" % "it,test"
}