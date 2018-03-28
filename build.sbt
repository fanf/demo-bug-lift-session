name := "Lift 3.0 starter template"

version := "0.1.0"

organization := "net.liftweb"

scalaVersion := "2.12.4"

resolvers ++= Seq(
  "snapshots"     at "https://oss.sonatype.org/content/repositories/snapshots",
  "releases"        at "https://oss.sonatype.org/content/repositories/releases"
)

enablePlugins(JettyPlugin)

unmanagedResourceDirectories in Test <+=  (baseDirectory) { _ / "src/main/webapp" }

scalacOptions ++= Seq("-deprecation", "-unchecked")

libraryDependencies ++= {
  val liftVersion = "3.0.2"
  Seq(
    "net.liftweb"       %% "lift-webkit"            % liftVersion,
    "net.liftmodules"   %% "lift-jquery-module_3.0" % "2.10",
    "javax.servlet"     %  "javax.servlet-api"      % "3.1.0"       % "provided",
    "ch.qos.logback"    %  "logback-classic"        % "1.2.3",
    "org.specs2"        %% "specs2-core"            % "3.9.4"       % "test"
  )
}

scalacOptions in Test ++= Seq("-Yrangepos")
