package semverfi

import scala.util.parsing.combinator.RegexParsers

object Parse extends RegexParsers {

  private val Dot = "."

  private val Plus = "+"

  private val Dash = "-"

  def id: Parser[String] = """[0-9A-Za-z-]+""".r

  def int: Parser[String] = """\d+""".r

  def version: Parser[SemVersion] =    
    buildVersion | preReleaseVersion | normalVersion

  def buildVersion: Parser[Build.Version] =
    versionTuple ~ classifier.? ~ (Plus ~> ids) ^^ {
      case ((major, minor, patch) ~ maybeClassifier ~ ids) =>
        Build.Version(major, minor, patch,
                      maybeClassifier.getOrElse(Nil) ++ ids)
    }

  def preReleaseVersion: Parser[PreRelease.Version] =
    versionTuple ~ classifier ^^ {
      case ((major, minor, patch) ~ classifier) =>
        PreRelease.Version(major, minor, patch, classifier)
    }

  def normalVersion: Parser[Normal.Version] =
    versionTuple ^^ {
      case (major, minor, patch) =>
        Normal.Version(major, minor, patch)
    }

  def versionTuple: Parser[(Int, Int, Int)] =
    int ~ (Dot ~> int) ~ (Dot ~> int) ^^ {
      case (maj ~ min ~ pat) =>
        (maj.toInt, min.toInt, pat.toInt)
  }

  def ids: Parser[Seq[String]] =
    rep1(id | Dot ~> id)

  def classifier: Parser[Seq[String]] =
    Dash ~> ids

  def apply(in: String): SemVersion =
    parseAll(version, in) match {
      case Success(v, _) => v
      case _ => Invalid(in)
    }

  def main(a: Array[String]) {
    println(Parse(a(0)))
  }
}
