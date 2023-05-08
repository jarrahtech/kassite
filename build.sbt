ThisBuild / scalaVersion := "3.2.2"
ThisBuild / organization := "com.jarrahtechnology"
ThisBuild / versionScheme := Some("early-semver")
ThisBuild / githubOwner := "jarrahtech"
ThisBuild / githubRepository := "kassite"

lazy val root = project.in(file(".")).
  aggregate(kassite.js, kassite.jvm).
  settings(
    publish := {},
    publishLocal := {},
  )

lazy val kassite = crossProject(JSPlatform, JVMPlatform).in(file("."))
  .enablePlugins(ScalablyTypedConverterGenSourcePlugin)
  .settings(
    name := "kassite",
    version := "0.1.0",    

    resolvers ++= Resolver.sonatypeOssRepos("public"),
    resolvers += Resolver.githubPackages("jarrahtech"),

    libraryDependencies += "org.scalatest" %%% "scalatest" % "3.2.15" % "test",
    libraryDependencies += "org.scalatest" %%% "scalatest-funsuite" % "3.2.15" % "test",

    libraryDependencies += "com.jarrahtechnology" %%% "jarrah-util" % "0.6.0",

    Compile / npmDependencies ++= Seq("babylonjs" -> "6.2.0"),
    stOutputPackage := "generated",
    stMinimize := Selection.None,
    Global / stQuiet := true,

    Test / logBuffered := false,    
  ).
  jvmSettings(
    scalacOptions ++= Seq(
      "-encoding", "utf8", 
      "-Xfatal-warnings",  
      "-deprecation",
    ),
    libraryDependencies += "org.scala-js" %% "scalajs-stubs" % "1.1.0" % "provided",
    Test / testOptions += Tests.Argument(TestFrameworks.ScalaTest, "-fW", "./target/scalatest.txt"),  
  ).
  jsSettings(
  )

lazy val kassiteJS = kassite.js
lazy val kassiteJVM = kassite.jvm