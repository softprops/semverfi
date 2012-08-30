package semverfi

import org.specs._

object ParseSpec extends Specification {
  "semver parse" should {
    "not parse garbage" in {
      Parse("1.0.0a").isInstanceOf[Parse.Failure] must be(true)
    }
    "parse normal versions" in {
      Parse("0.1.0") match {
        case Parse.Success(r, _) => r must_== NormalVersion(0,1,0)
        case a => fail("unexpected parse result %s" format a)
      }
    }
    "parse pre-release versions" in {
      Parse("0.1.1-alpha.1") match {
        case Parse.Success(r, _) =>
            r must_== PreReleaseVersion(0, 1, 1, Seq("alpha","1"))
        case a => fail("unexpected parse result %s" format a)
      }
    }
    "parse build versions with classifiers" in {
      Parse("0.1.1-alpha.1+a") match {
        case Parse.Success(r , _) =>
            r must_== BuildVersion(0, 1, 1, Seq("alpha", "1"), Seq("a"))
        case a => fail("unexpected parse result %s" format a)
      }
    }
    "parse build versions without classifiers" in {
      Parse("0.1.1+a") match {
        case Parse.Success(r, _) => r must_== BuildVersion(0, 1, 1, Nil, Seq("a"))
        case a => fail("unexpected parse result %s" format a)
      }
    }
  }
}
