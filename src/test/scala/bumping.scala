package semverfi

import org.specs._

object BumpSpec extends Specification {

  "bumping versions" should {
    "clear minor and patch when major changes" in {
      NormalVersion(1, 1, 3).bumpMajor must_== NormalVersion(2, 0, 0)
    }
    "clear patch but not major when minor changes" in {
      NormalVersion(1, 1, 3).bumpMinor must_== NormalVersion(1, 2, 0)
    }
    "not clear anything when patch changes" in {
      NormalVersion(1, 1, 3).bumpPatch must_== NormalVersion(1, 1, 4)
    }
  }
}
