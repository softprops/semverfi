package semverfi

import scala.util.parsing.combinator.JavaTokenParsers

object Parse extends JavaTokenParsers {

  private val Dot = "."

  private val Plus = "+"

  private val Dash = "-"

  def id: Parser[String] = """[\w|\d]+""".r

  def int: Parser[String] = """\d+""".r

  def any: Parser[String] = """.""".r

  def version: Parser[SemVersion] =
    buildVersion | preReleaseVersion | normalVersion | invalidVersion

  def buildVersion: Parser[Build.Version] =
    versionTuple ~ classifier.? ~ Plus ~ rep(id | id <~ Dot) ^^ {
      case ((maj, min, pat) ~ maybeClassifier ~ _ ~ ids) =>
        println("parsed build")
        Build.Version(maj, min, pat,
                      ids ++ maybeClassifier.getOrElse(Nil))
    }

  def preReleaseVersion: Parser[PreRelease.Version] =
    versionTuple ~ classifier ^^ {
      case ((maj, min, pat) ~ classifier) =>
        println("parsed pre-release")
        PreRelease.Version(maj, min, pat, classifier)
    }

  def invalidVersion: Parser[Invalid] =
    rep(any) ^^ {
      case chars =>
        println("parsed invalid")
        Invalid(chars.mkString(""))
    }

  def normalVersion: Parser[Normal.Version] =
    versionTuple ^^ {
      case (major, minor, patch) =>
        println("parsed normal")
        Normal.Version(major, minor, patch)
    }

  def versionTuple: Parser[(Int, Int, Int)] =
    (int ~ Dot ~ int ~ Dot ~ int) ^^ {
      case (maj ~ _ ~ min ~ _ ~ pat) =>
        println("parsed version tuple")
        (maj.toInt, min.toInt, pat.toInt)
  }

  def classifier: Parser[Seq[String]] =
    Dash ~ rep(id | Dot ~> id) ^^ {
      case (_ ~ ids) =>
        println("parsed classifier")
        ids
    }

  def apply(in: String): ParseResult[SemVersion] =
    parseAll(version, in)

  def main(a: Array[String]) {
    println(Parse(a(0)))
  }
}
