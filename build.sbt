organization := "me.lessis"

version := "0.1.0-SNAPSHOT"

name := "semverfi"

description := "Always Faithful, always loyal semantic versions"

homepage := Some(url("https://github.com/softprops/semverfi"))

libraryDependencies <+= scalaVersion( v =>
  (v.split("[.-]").toList match {
    case "2" :: "8" :: _ => "org.scala-tools.testing" % "specs_2.8.1" % "1.6.8"
    case "2" :: "9" :: "0" :: "1" :: _ => "org.scala-tools.testing" %% "specs" % "1.6.8"
    case "2" :: "9" :: _ => "org.scala-tools.testing" % "specs_2.9.1" % "1.6.9"
    case _ => sys.error("specs not supported for scala version %s" format v)
  }) % "test"
)

publishTo := Some(Opts.resolver.sonatypeReleases)

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


