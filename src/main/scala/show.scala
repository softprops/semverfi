package semverfi

trait Show[T <: SemVersion] {
  def show(v: T): String
}

object Show {
  implicit object ShowNormal extends Show[NormalVersion] {
    def show(v: NormalVersion) = "%d.%d.%d" format(
      v.major, v.minor, v.patch
    )
  }
  implicit object ShowPreRelease extends Show[PreReleaseVersion] {
    def show(v: PreReleaseVersion) = "" format(
      v.major, v.minor, v.patch
    )
  }
  implicit object ShowBuild extends Show[BuildVersion] {
    def show(v: BuildVersion) = "" format(
      v.major, v.minor, v.patch
    )
  }
  def show[T <: SemVersion: Show](v: T) =
    implicitly[Show[T]].show(v)
}
