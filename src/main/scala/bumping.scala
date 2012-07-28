package semverfi

sealed trait Digit
case object Major extends Digit
case object Minor extends Digit
case object Patch extends Digit

trait Bumping { self: SemVersion =>
  def inc(d: Digit) = d match {
    case Major => self match {
      case n @ Normal.Version(m, _, _) =>
        n.copy(major = m + 1, minor = 0, patch = 0)
      case _ => self 
    }
    case Minor => self match {
      case n @ Normal.Version(_, m, _) =>
        n.copy(minor = m + 1, patch = 0)
      case _ => self
    }
    case Patch => self match {
      case n @ Normal.Version(_, _, p) =>
        n.copy(patch = p + 1)
      case _ => self
    }
  }
  def dec(d: Digit) = d match {
    case Major => self match {
      case n @ Normal.Version(m, _, _) if (m > 1) =>
        n.copy(major = m - 1)
      case _ => self 
    }
    case Minor => self match {
      case n @ Normal.Version(_, m, _) if (m > 1) =>
        n.copy(minor = m - 1)
      case _ => self
    }
    case Patch => self match {
      case n @ Normal.Version(_, _, p) if (p > 1) =>
        n.copy(patch = p - 1)
      case _ => self
    }
  }
}
