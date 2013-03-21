package semverfi

/**
 * Provides a means to render a normal
 * version from a preprelease version
 */
trait Normalizing { self: Valid =>
  def normalize = self match {
    case n: NormalVersion => n
    case v: Valid =>
      NormalVersion(v.major, v.minor, v.patch)
    case invalid => invalid
  }
}
