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

object Normal {
  abstract class AbstractVersion(major: Int, minor: Int, patch: Int)
           extends SemVersion
  case class Version(major: Int, minor: Int, patch: Int)
       extends AbstractVersion(major, minor, patch)
}

object PreRelease {
  case class Version(major: Int,
                     minor: Int,
                     patch: Int,
                     extras: Seq[String])
       extends Normal.AbstractVersion(major, minor, patch)
}

object Build {
  case class Version(major: Int,
                     minor: Int,
                     patch: Int,
                     extras: Seq[String])
       extends Normal.AbstractVersion(major, minor, patch)
}
