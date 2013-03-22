package semverfi

import org.specs._

object OptSpec extends Specification {
  "transforming to parsed versions" should {
    "have no effect on invalid versions" in {
      Version("junk").opt.map(_.prerelease("SNAPSHOT")) mustBe None
    }
    "have an effect on valid versions" in {
      Version("0.1.0").opt.map(_.prerelease("SNAPSHOT")) must_== Some(
        PreReleaseVersion(0, 1, 0, Seq("SNAPSHOT")))
    }
  }
}

