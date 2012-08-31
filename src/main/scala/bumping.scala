package semverfi

sealed trait Digit
case object Major extends Digit
case object Minor extends Digit
case object Patch extends Digit

trait Bumping { self: SemVersion =>

  def bumpMajor = self match {
    case v @ NormalVersion(m, _, _) =>
      v.copy(major = m + 1, minor = 0, patch = 0)
    case v @ PreReleaseVersion(m, _, _, _) =>
      v.copy(major = m + 1, minor = 0, patch = 0)
    case v @ BuildVersion(m, _, _, _, _) =>
      v.copy(major = m + 1, minor = 0, patch = 0)
    case _ => self
  }

  def bumpMinor = self match {
    case v @ NormalVersion(_, m, _) =>
      v.copy(minor = m + 1, patch = 0)
    case v @ PreReleaseVersion(_, m, _, _) =>
      v.copy(minor = m + 1, patch = 0)
    case v @ BuildVersion(_, m, _, _, _) =>
      v.copy(minor = m + 1, patch = 0)
    case _ => self
  }

  def bumpPatch = self match {
    case v @ NormalVersion(_, _, p) =>
      v.copy(patch = p + 1)
    case v @ PreReleaseVersion(_, _, p, _) =>
      v.copy(patch = p + 1)
    case v @ BuildVersion(_, _, p, _, _) =>
      v.copy(patch = p + 1)
    case _ => self
  }
}
