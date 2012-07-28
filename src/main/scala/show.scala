package semverfi

trait Show[T <: SemVersion] {
  def show(v: T): String
}

object Show {
  implicit object ShowNormal extends Show[Normal.Version] {
    def show(v: Normal.Version) = "%d.%d.%d" format(
      v.major, v.minor, v.patch
    )
  }
  implicit object ShowPreRelease extends Show[PreRelease.Version] {
    def show(v: PreRelease.Version) = "" format(
      v.major, v.minor, v.patch
    )
  }
  implicit object ShowBuild extends Show[Build.Version] {
    def show(v: Build.Version) = "" format(
      v.major, v.minor, v.patch
    )
  }
  def show[T <: SemVersion: Show](v: T) =
    implicitly[Show[T]].show(v)
}
