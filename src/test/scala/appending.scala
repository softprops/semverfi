package semverfi

import org.specs._

object AppendingSpec extends Specification {
  "appending to versions" should {
    "append prerelease classifiers" in {
      NormalVersion(0,1,0).prerelease("SNAPSHOT") must_== PreReleaseVersion(0,1,0, Seq("SNAPSHOT"))
    } 
    "append build info" in {
      PreReleaseVersion(0,1,0, Seq("SNAPSHOT")).build("123") must_== BuildVersion(0,1,0, Seq("SNAPSHOT"), Seq("123"))
    }
  } 
}
