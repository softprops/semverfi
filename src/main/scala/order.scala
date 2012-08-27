package semverfi

trait SemVersionOrdering extends Ordering[SemVersion] {

  def compare(self: SemVersion, that: SemVersion): Int =
    (self, that) match {
      case (a: NormalVersion, b: NormalVersion) =>
        byNormal(a, b)
      case (a: NormalVersion, b: PreReleaseVersion) =>
        byNormal(a, b) match {
          case 0 => 1
          case c => c
        }
      case (a: NormalVersion, b: BuildVersion) =>
        byNormal(a, b) match {
          case 0 =>
            if (b.unclassified) -1
            else 1
          case c => c
        }
      case (a: PreReleaseVersion, b: NormalVersion) =>
        byNormal(a, b) match {
          case 0 => -1
          case c => c
        }
      case (a: PreReleaseVersion, b: PreReleaseVersion) =>
        byNormal(a, b) match {
          case 0 =>
            byIds(a.classifier, b.classifier)
          case c => c
        }
      case (a: PreReleaseVersion, b: BuildVersion) =>
        byNormal(a, b) match {
          case 0 =>
            if (b.unclassified) -1
            else byIds(a.classifier, b.classifier)
          case c => c
        }
      case (a: BuildVersion, b: NormalVersion) =>
        byNormal(a, b) match {
          case 0 =>
            if (a.unclassified) 1
            else -1
          case c => c
        }
      case (a: BuildVersion, b: PreReleaseVersion) =>
        byNormal(a, b) match {
          case 0 =>
            if (a.unclassified) 1
            else byIds(a.classifier, b.classifier) match {
              case 0 => 1
              case c => c
            }
          case c => c
        }
      case (a: BuildVersion, b: BuildVersion) =>
        byNormal(a, b) match {
          case 0 =>
            if (a.unclassified || b.unclassified) {
              if (a.unclassified && b.classified) 1
              else if (a.classified && b.unclassified) -1
              else byIds(a.build, b.build)
            } else {
              byIds(a.classifier, b.classifier) match {
                case 0 =>
                  byIds(a.build, b.build)
                case c => c
              }
            }
          case c => c
        }
    }

  private def byNormal(a: AbstractVersion, b: AbstractVersion) =
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
