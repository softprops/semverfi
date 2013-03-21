package semverfi

object Version {
  def apply(in: String) = Parse(in)
}

/** these get put at the bottom of ordered results */
sealed trait SemVersion extends SemVersionOrdering {
  def major: Int
  def minor: Int
  def patch: Int
  def opt: Option[Valid]
  override def toString = Show(this)
}

case class Invalid(raw: String) extends SemVersion {
  def major = -1
  def minor = -1
  def patch = -1
  def opt = None
}

sealed trait Valid extends SemVersion
        with Appending
        with Bumping
        with Normalizing {
  def opt = Some(this)
}

case class NormalVersion(major: Int, minor: Int, patch: Int)
   extends Valid

case class PreReleaseVersion(
  major: Int,
  minor: Int,
  patch: Int,
  classifier: Seq[String])
   extends Valid

case class BuildVersion(
  major: Int,
  minor: Int,
  patch: Int,
  classifier: Seq[String],
  build: Seq[String])
 extends Valid {
    lazy val classified = ! classifier.isEmpty
    lazy val unclassified = ! classified
}
