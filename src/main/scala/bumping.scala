package semverfi

sealed trait Digit
case object Major extends Digit
case object Minor extends Digit
case object Patch extends Digit

/**
 * The act of bumping produces a new NormalVersion,
 *  discarding any previous pre-release and build suffixes
 */
trait Bumping { self: SemVersion =>

  def bumpMajor = self match {
    case v @ NormalVersion(m, _, _) =>
      v.copy(major = m + 1, minor = 0, patch = 0)
    case v @ PreReleaseVersion(m, _, _, _) =>
      NormalVersion(major = m + 1, minor = 0, patch = 0)
    case v @ BuildVersion(m, _, _, _, _) =>
      NormalVersion(major = m + 1, minor = 0, patch = 0)
    case _ => self
  }

  def bumpMinor = self match {
    case v @ NormalVersion(_, m, _) =>
      v.copy(minor = m + 1, patch = 0)
    case v @ PreReleaseVersion(maj, m, _, _) =>
      NormalVersion(major = maj, minor = m + 1, patch = 0)
    case v @ BuildVersion(maj, m, _, _, _) =>
      NormalVersion(major = maj, minor = m + 1, patch = 0)
    case _ => self
  }

  def bumpPatch = self match {
    case v @ NormalVersion(_, _, p) =>
      v.copy(patch = p + 1)
    case v @ PreReleaseVersion(maj, min, p, _) =>
      NormalVersion(major = maj, minor = min, patch = p + 1)
    case v @ BuildVersion(maj, min, p, _, _) =>
      NormalVersion(major = maj, minor = min, patch = p + 1)
    case _ => self
  }
}
