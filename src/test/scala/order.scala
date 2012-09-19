package semverfi

import org.specs._
import scala.util.Random.shuffle

object OrderSpec extends Specification {
  "ordered versions" should {
    "be ordered according to the semver spec" in {
      val expected = List(
        "1.0.0-alpha", "1.0.0-alpha.1",  "1.0.0-beta.2", "1.0.0-beta.11",
        "1.0.0-rc.1", "1.0.0-rc.1+build.1", "1.0.0", "1.0.0+0.3.7", "1.3.7+build",
        "1.3.7+build.2.b8f12d7", "1.3.7+build.11.e0f985a")
        .map (Version.apply)
      val shuffled = shuffle(expected)
      shuffled.sorted must_== expected
    }
    "produce a correct max value" in {
       shuffle(List(
        "1.0.0-alpha", "1.0.0-alpha.1",  "1.0.0-beta.2", "1.0.0-beta.11",
        "1.0.0-rc.1", "1.0.0-rc.1+build.1", "1.0.0", "1.0.0+0.3.7", "1.3.7+build",
        "1.3.7+build.2.b8f12d7", "1.3.7+build.11.e0f985a")
        .map (Version.apply)).max must_== BuildVersion(1, 3, 7, Seq(),
                                                       Seq("build", "11", "e0f985a"))
    }
    "produce a correct min value" in {
      shuffle(List(
        "1.0.0-alpha", "1.0.0-alpha.1",  "1.0.0-beta.2", "1.0.0-beta.11",
        "1.0.0-rc.1", "1.0.0-rc.1+build.1", "1.0.0", "1.0.0+0.3.7", "1.3.7+build",
        "1.3.7+build.2.b8f12d7", "1.3.7+build.11.e0f985a")
        .map (Version.apply)).min must_== PreReleaseVersion(1, 0, 0,
                                                              Seq("alpha"))
    }
  }
  "specific examples" should {
    "order 2.1 after 1.2.1" in {
      val v1 = Version("1.2.1")
      val v2 = Version("2.1")
      v1 must beLessThan(v2)
      v2 must beGreaterThan(v1)
      v1 must_!= v2
    }
    "order 2 after 1.2.1" in {
      val v1 = Version("1.2.1")
      val v2 = Version("2")
      v1 must beLessThan(v2)
      v2 must beGreaterThan(v1)
      v1 must_!= v2
    }
  }
}
