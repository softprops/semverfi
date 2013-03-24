organization := "me.lessis"

version := "0.1.2"

name := "semverfi"

description := "Always Faithful, always loyal semantic versions"

homepage := Some(url("https://github.com/softprops/semverfi"))

crossScalaVersions := Seq("2.9.1-1", "2.9.2",
                          "2.10.0", "2.10.1")

scalacOptions += "-deprecation"

libraryDependencies <+= scalaVersion( v =>
  (v.split("[.-]").toList match {
    case "2" :: "9" :: _ => "org.scala-tools.testing" % "specs_2.9.1" % "1.6.9"
    case _ => "org.scala-tools.testing" %% "specs" % "1.6.9"
  }) % "test"
)

publishMavenStyle := true

publishTo := Some(Opts.resolver.sonatypeStaging)

publishArtifact in Test := false

licenses <<= version(v => Seq("MIT" -> url("https://github.com/softprops/semverfi/blob/%s/LICENSE" format v)))

pomExtra := (
  <scm>
    <url>git@github.com:softprops/semverfi.git</url>
    <connection>scm:git:git@github.com:softprops/semverfi.git</connection>
  </scm>
  <developers>
    <developer>
      <id>softprops</id>
      <name>Doug Tangren</name>
      <url>https://github.com/softprops</url>
    </developer>
  </developers>)

//seq(lsSettings:_*)

//LsKeys.tags in LsKeys.lsync := Seq("semver", "version")
