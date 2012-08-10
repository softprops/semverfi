package semverfi

trait SemVersionOrdering extends Ordering[SemVersion] {

  def compare(self: SemVersion, that: SemVersion): Int =
    (self, that) match {
      case (a: Normal.Version, b: Normal.Version) =>
        byNormal(a, b)
      case (a: Normal.Version, b: PreRelease.Version) =>
        byNormal(a, b) match {
          case 0 => 1
          case c => c
        }
      case (a: Normal.Version, b: Build.Version) =>
        byNormal(a, b) match {
          case 0 => -1
          case c => c
        }
      case (a: PreRelease.Version, b: Normal.Version) =>
        byNormal(a, b) match {
          case 0 => -1
          case c => c
        }
      case (a: PreRelease.Version, b: PreRelease.Version) =>
        byNormal(a, b) match {
          case 0 =>
            byIds(a.extras, b.extras)
          case c => c
        }
      case (a: PreRelease.Version, b: Build.Version) =>
        byNormal(a, b) match {
          case 0 =>
            byIds(a.extras, b.extras)
          case c => c
        }
      case (a: Build.Version, b: Normal.Version) =>
        byNormal(a, b) match {
          case 0 => 1
          case c => c
        }
      case (a: Build.Version, b: PreRelease.Version) =>
        byNormal(a, b) match {
          case 0 =>
            byIds(a.extras, b.extras)
          case c => c
        }
      case (a: Build.Version, b: Build.Version) =>
        byNormal(a, b) match {
          case 0 =>
            byIds(a.extras, b.extras)
          case c => c
        }
    }

  private def byNormal(a: Normal.AbstractVersion, b: Normal.AbstractVersion) =
    a.major.compareTo(b.major) match {
      case 0 => a.minor.compareTo(b.minor) match {
        case 0 =>
          a.patch.compareTo(b.patch)
        case mic => mic
      }
      case mac => mac
    }

  private def byIds(a: Seq[String], b: Seq[String]): Int = {
    val Dig = """(\d+)""".r
    def zipCompare(a: Seq[String], b: Seq[String]) =
       ((None: Option[Int]) /: a.zip(b)) {
         case (Some(c), _) => Some(c)
         case (_, (Dig(k), Dig(v))) =>
           (k.toInt compareTo v.toInt) match {
             case 0 => None
             case c => Some(c)
           }
         case (_, (Dig(k), v)) => Some(1)
         case (_, (k, Dig(v))) => Some(-1)
         case (_, (k, v)) => (k compareTo v) match {
           case 0 => None
           case c => Some(c)
         }
       }
    zipCompare(a, b).getOrElse(0) match {
      case 0 if (a.size > b.size) => 1
      case 0 if (a.size < b.size) => -1
      case c => c
    }
  }
}
