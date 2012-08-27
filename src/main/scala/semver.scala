package semverfi

// http://semver.org/

object Version {
  def apply(in: String): Either[Invalid, SemVersion] =
    Parse(in) match {
      case i: Invalid => Left(i)
      case v => Right(v)
    }
}

abstract class SemVersion extends Bumping {
  def major: Int
  def minor: Int
  def patch: Int
}

case class Invalid(raw: String) extends SemVersion {
  def major = -1
  def minor = -1
  def patch = -1
}

abstract class AbstractVersion(major: Int, minor: Int, patch: Int)
         extends SemVersion

case class NormalVersion(major: Int, minor: Int, patch: Int)
     extends AbstractVersion(major, minor, patch)


case class PreReleaseVersion(major: Int,
                     minor: Int,
                     patch: Int,
                     classifier: Seq[String])
     extends AbstractVersion(major, minor, patch)

case class BuildVersion(major: Int,
                     minor: Int,
                     patch: Int,
                     classifier: Seq[String],
                     build: Seq[String])
  extends AbstractVersion(major, minor, patch) {
    lazy val classified = ! classifier.isEmpty
    lazy val unclassified = ! classified
}
