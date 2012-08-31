# semverfi

[![Build Status](https://secure.travis-ci.org/softprops/semverfi.png)](http://travis-ci.org/softprops/semverfi)

A library for parsing, querying, and ordering the always faithful, always loyal [semantic versions][sv]

## usage

Get the major version from a string

```scala
import semverfi._

Version("1.0.1-alpha.1").major
```

Order versions

```scala
import semverfi._
import scala.util.Random.shuffle

val expected = List("1.0.0-alpha", "1.0.0-alpha.1",  "1.0.0-beta.2", "1.0.0-beta.11", "1.0.0-rc.1", "1.0.0-rc.1+build.1", "1.0.0", "1.0.0+0.3.7", "1.3.7+build", "1.3.7+build.2.b8f12d7", "1.3.7+build.11.e0f985a")

val shuffled = shuffle(expected)

val parsed = suffled.map(Version.apply)

// print list of sorted versions
parsed.sorted.foreach(println)
```

Doug Tangren (softprops) 2012

[sv]: http://semver.org/
