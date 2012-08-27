package semverfi

import org.specs._

object ParseSpec extends Specification {
  "semver parse" should {
    "not parse garbage" in {
      val garbage = "1.0.0a"
      Parse(garbage) must_== Invalid(garbage)
    }
    "parse normal versions" in {
      Parse("0.1.0") must_== NormalVersion(0,1,0)
    }
    "parse pre-release versions" in {
      Parse("0.1.1-alpha.1") must_== PreReleaseVersion(0, 1, 1, Seq("alpha","1"))
    }
    "parse build versions with classifiers" in {
      Parse("0.1.1-alpha.1+a") must_== BuildVersion(0, 1, 1, Seq("alpha", "1"), Seq("a"))
    }
    "parse build versions without classifiers" in {
      Parse("0.1.1+a") must_== BuildVersion(0, 1, 1, Nil, Seq("a"))
    }
  }
}
