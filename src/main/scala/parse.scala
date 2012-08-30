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

  def buildVersion: Parser[BuildVersion] =
    versionTuple ~ classifier.? ~ (Plus ~> ids) ^^ {
      case ((major, minor, patch) ~ maybeClassifier ~ build) =>
        BuildVersion(major, minor, patch,
                     maybeClassifier.getOrElse(Nil),
                     build)
    }

  def preReleaseVersion: Parser[PreReleaseVersion] =
    versionTuple ~ classifier ^^ {
      case ((major, minor, patch) ~ classifier) =>
        PreReleaseVersion(major, minor, patch,
                          classifier)
    }

  def normalVersion: Parser[NormalVersion] =
    versionTuple ^^ {
      case (major, minor, patch) =>
        NormalVersion(major, minor, patch)
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

  def apply(in: String) =
    parseAll(version, in)
}
