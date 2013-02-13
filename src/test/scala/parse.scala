package semverfi

import org.specs._

object ParseSpec extends Specification {
  "semver parse" should {
    "parse 'garbage' as invalid version" in {
      Parse("1.0.0a") must_== Invalid("1.0.0a")
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
    "parse invalid version" in {
      Parse("2.4+Problem_case") must_== Invalid("2.4+Problem_case")
    }
    "parse short form version" in {
      Parse("2.1") must_== NormalVersion(2,1,0)
    }
    "parse very short form version" in {
      Parse("2") must_== NormalVersion(2,0,0)
    }
    "parse versions prefixed with `v`" in {
      Parse("v0.1.0") must_== NormalVersion(0,1,0)
    }
  }
}
