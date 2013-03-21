package semverfi

sealed trait Digit
case object Major extends Digit
case object Minor extends Digit
case object Patch extends Digit

/**
 * The act of bumping produces a new NormalVersion,
 *  discarding any previous pre-release and build suffixes
 */
trait Bumping { self: Valid =>

  def bumpMajor: Valid = self match {
    case v: NormalVersion =>
      v.copy(major = v.major + 1, minor = 0, patch = 0)
    case v: Valid =>
      v.normalize.bumpMajor
    case _ => self
  }

  def bumpMinor: Valid = self match {
    case v: NormalVersion =>
      v.copy(minor = v.minor + 1, patch = 0)
    case v: Valid =>
      v.normalize.bumpMinor
    case _ => self
  }

  def bumpPatch: Valid = self match {
    case v: NormalVersion =>
      v.copy(patch = v.patch + 1)
    case v: Valid =>
      v.normalize.bumpPatch
    case _ => self
  }
}
