package semverfi

/**
 * Provides a means to append a prelease classifier and/or build info
 * to versions.
 */ 
trait Appending { self: Valid =>
  def prerelease(classifier: String*) = self match {
    case n: NormalVersion =>
      PreReleaseVersion(
        n.major, n.minor, n.patch,
        classifier = classifier.toSeq)
    case p: PreReleaseVersion =>
      p.copy(classifier = classifier.toSeq)
    case b: BuildVersion =>
      PreReleaseVersion(
        b.major, b.minor, b.patch,
        classifier = classifier.toSeq)
    case invalid => invalid
  }

  def build(info: String*) = self match {
    case n: NormalVersion =>
      BuildVersion(n.major, n.minor, n.patch,
                   Seq.empty[String], build = info.toSeq)
    case p: PreReleaseVersion =>
      BuildVersion(p.major, p.minor, p.patch,
                   classifier = p.classifier, build = info.toSeq)
    case b: BuildVersion =>
      b.copy(build = info.toSeq)
    case invalid => invalid
  }
}
