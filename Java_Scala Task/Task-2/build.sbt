name := "Task-2"

version := "0.1"

scalaVersion := "2.12.4"

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.0.5" % Test,
  "org.typelevel" %% "cats-core" % "1.1.0",
  "eu.timepit" %% "refined-cats" % "0.8.7",
  "com.github.julien-truffaut" %%  "monocle-core"  % "1.5.0",
  "com.github.julien-truffaut" %%  "monocle-macro" % "1.5.0",
  "com.dragishak" %% "monocle-cats" % "1.2")
