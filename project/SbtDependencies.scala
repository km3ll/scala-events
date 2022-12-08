import sbt._

object SbtDependencies {

  val basic: Seq[ModuleID] = Seq(
    "org.typelevel"               %% "cats-core"       % "2.0.0",
    "org.typelevel"               %% "cats-effect"     % "2.0.0",
    "org.apache.commons"          %  "commons-lang3"   % "3.3.2",
    "com.typesafe"                %  "config"          % "1.3.2",
    "ch.qos.logback"              %  "logback-classic" % "1.1.3",
    "io.monix"                    %% "monix"           % "2.3.3",
    "org.scalacheck"              %% "scalacheck"      % "1.14.0",
    "org.scalatest"               %% "scalatest"       % "3.0.5",
    "com.typesafe.scala-logging"  %% "scala-logging"   % "3.9.2",
    "com.github.pureconfig"       %% "pureconfig"      % "0.17.1"
  )

  val events: Seq[ModuleID] = Seq(
    "com.spingo"                  %% "op-rabbit-core"   % "2.1.0",
    "joda-time"                   % "joda-time"         % "2.9.1"
  ) ++ basic

}