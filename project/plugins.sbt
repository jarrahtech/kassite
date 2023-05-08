
// https://www.scala-sbt.org/1.x/docs/Plugins.html

//conflictManager := ConflictManager.latestRevision
//dependencyOverrides += "org.scala-lang.modules" %% "scala-xml" % "2.1.0"

addSbtPlugin("com.codecommit" % "sbt-github-packages" % "0.5.3")
addSbtPlugin("org.scala-js" % "sbt-scalajs" % "1.13.1")
addSbtPlugin("org.portable-scala" % "sbt-scalajs-crossproject" % "1.3.0")
addSbtPlugin("org.scalablytyped.converter" % "sbt-converter" % "1.0.0-beta41")
