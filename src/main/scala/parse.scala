package semverfi

import scala.util.parsing.combinator.RegexParsers

object Parse extends RegexParsers {

  private val Dot = "."

  private val Plus = "+"

  private val Dash = "-"

  def id: Parser[String] = """[0-9A-Za-z-]+""".r

  def int: Parser[String] = """\d+""".r

  def any: Parser[String] = """.+""".r

  def version: Parser[SemVersion] =
    validVersion | log(invalidVersion)("inv version")

  def validVersion: Parser[SemVersion] =    
    buildVersion | preReleaseVersion | normalVersion

  def buildVersion: Parser[Build.Version] =
    versionTuple ~ classifier.? ~ (Plus ~> classifierSegs) ^^ {
      case ((major, minor, patch) ~ maybeClassifier ~ ids) =>
        Build.Version(major, minor, patch,
                      maybeClassifier.getOrElse(Nil) ++ ids)
    }

  def preReleaseVersion: Parser[PreRelease.Version] =
    versionTuple ~ classifier ^^ {
      case ((major, minor, patch) ~ classifier) =>
        PreRelease.Version(major, minor, patch, classifier)
    }

  def invalidVersion: Parser[Invalid] =
    any.* ^^ {
      case cs => Invalid(cs mkString(""))
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

  def classifierSegs: Parser[Seq[String]] =
    rep1(id | Dot ~> id)

  def classifier: Parser[Seq[String]] =
    Dash ~> classifierSegs

  def apply(in: String): ParseResult[SemVersion] =
    parseAll(version, in)

  def main(a: Array[String]) {
    println(Parse(a(0)))
  }
}
