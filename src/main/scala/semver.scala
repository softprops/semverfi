package semverfi

// http://semver.org/

abstract class SemVersion extends/* Ordered[SemVersion] with*/ Bumping {
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
  val PatternBase =
                ("""(\d+)"""+ // major
                "[.]"      +
                """(\d+)"""+ // minor
                "[.]"      +
                """(\d+)""") // patch
  val Pattern = PatternBase.r
  case class Version(major: Int, minor: Int, patch: Int)
       extends SemVersion
}

object PreRelease {
  val Pattern = (Normal.PatternBase +
                "-" +
                """([0-9A-Za-z-.]+)""").r
  case class Version(maj: Int,
                     min: Int,
                     pat: Int,
                     extras: Seq[String])
       extends Normal.Version(maj, min, pat)
}

object Build {
  val Pattern = (Normal.PatternBase +
                "+" +
                """([0-9A-Za-z-.]+)""").r
  case class Version(maj: Int,
                     min: Int,
                     pat: Int,
                     extras: Seq[String])
       extends Normal.Version(maj, min, pat)
}

object Version {
  def parse(in: String): Either[Invalid, SemVersion] =
    in match {
      case Normal.Pattern(ma, mi, pa) =>
        Right(Normal.Version(ma.toInt, mi.toInt, pa.toInt))
      case PreRelease.Pattern(ma, mi, pa, ex) =>
        Right(PreRelease.Version(ma.toInt, mi.toInt, pa.toInt,
                                 ex.split('.').toSeq))
      case Build.Pattern(ma, mi, pa, ex) =>
        Right(Build.Version(ma.toInt, mi.toInt, pa.toInt,
                            ex.split('.').toSeq))
      case _ => Left(Invalid(in))
    }
}
