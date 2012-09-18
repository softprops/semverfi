package semverfi

object Version {
  def apply(in: String): SemVersion = Parse(in)
}

sealed trait SemVersion extends SemVersionOrdering {
  def major: Int
  def minor: Int
  def patch: Int
  override def toString = Show(this)
}

case class Invalid(raw: String) extends SemVersion {
  def major = -1
  def minor = -1
  def patch = -1
}

abstract class Valid extends SemVersion with Bumping

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
