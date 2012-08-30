package semverfi

// http://semver.org/

object Version {
  def apply(in: String): SemVersion =
     Parse(in) match {
      case Parse.Success(v, _) => v
      case _ => Invalid(in)
    }
}

sealed trait SemVersion

case class Invalid(raw: String) extends SemVersion

abstract class Valid extends SemVersion with Bumping {
  def major: Int
  def minor: Int
  def patch: Int
}

case class NormalVersion(major: Int, minor: Int, patch: Int)
     extends Valid

case class PreReleaseVersion(major: Int,
                     minor: Int,
                     patch: Int,
                     classifier: Seq[String])
     extends Valid

case class BuildVersion(major: Int,
                     minor: Int,
                     patch: Int,
                     classifier: Seq[String],
                     build: Seq[String])
  extends Valid {
    lazy val classified = ! classifier.isEmpty
    lazy val unclassified = ! classified
}
