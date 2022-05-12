val Http4sVersion = "0.23.11"
val CirceVersion = "0.14.1"
val MunitVersion = "0.7.29"
val LogbackVersion = "1.2.10"
val MunitCatsEffectVersion = "1.0.7"
val DoobieVersion = "1.0.0-RC1"
val NewTypeVersion = "0.4.4"
// val Fs2Version = "3.2.7"

lazy val root = (project in file("."))
  .settings(
    organization := "com.example",
    name := "todo",
    version := "0.0.1-SNAPSHOT"
  )

lazy val server = (project in file("server"))
  .enablePlugins(FlywayPlugin)
  .settings(
    scalaVersion := "2.13.8",

    // Only necessary for SNAPSHOT releases
    // resolvers += Resolver.sonatypeRepo("snapshots"),

    libraryDependencies ++= Seq(
      "org.http4s" %% "http4s-ember-server" % Http4sVersion,
      "org.http4s" %% "http4s-ember-client" % Http4sVersion,
      "org.http4s" %% "http4s-circe" % Http4sVersion,
      "org.http4s" %% "http4s-dsl" % Http4sVersion,
      "io.circe" %% "circe-generic" % CirceVersion,
      "org.scalameta" %% "munit" % MunitVersion % Test,
      "org.typelevel" %% "munit-cats-effect-3" % MunitCatsEffectVersion % Test,
      "ch.qos.logback" % "logback-classic" % LogbackVersion % Runtime,
      "org.scalameta" %% "svm-subs" % "20.2.0",
      "org.tpolecat" %% "doobie-core" % DoobieVersion,
      "org.tpolecat" %% "doobie-postgres" % DoobieVersion,
      "org.tpolecat" %% "doobie-specs2" % DoobieVersion,
      "org.tpolecat" %% "doobie-hikari" % DoobieVersion,
      "io.estatico" %% "newtype" % NewTypeVersion
      // "co.fs2" %% "fs2-core" % Fs2Version
    ),
    addCompilerPlugin(
      "org.typelevel" %% "kind-projector" % "0.13.2" cross CrossVersion.full
    ),
    addCompilerPlugin("com.olegpy" %% "better-monadic-for" % "0.3.1"),
    testFrameworks += new TestFramework("munit.Framework"),
    flywayUrl := "jdbc:postgresql:todo",
    flywayUser := "postgres",
    flywayPassword := "1111",
    flywayLocations += "db.migration",
    flywayBaselineOnMigrate := true,
    Test / flywayUrl := "jdbc:postgresql:todo-test",
    Test / flywayUser := "postgres",
    Test / flywayPassword := "1111",
    Test / flywayLocations += "db.migration",
    Test / flywayBaselineOnMigrate := true
  )

lazy val client = (project in file("client"))
