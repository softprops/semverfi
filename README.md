# semverfi

A library for parsing, querying, and ordering the always faithful, always loyal [semantic versions][sv]

## usage

Get the major version from a string

```scala
import semverfi._

// prints 1
Version("0.1.0").fold(identity, (v => println(v.minor)))
```

Bump the major version

```scala
import semverfi._

// prints Version(1,0,0)
Version("0.1.0").fold(identity, (v => println(v.inc(Major))))
```

Order versions

```scala
import semverfi._
import scala.util.Random.shuffle

val expected = List("1.0.0-alpha", "1.0.0-alpha.1",  "1.0.0-beta.2", "1.0.0-beta.11", "1.0.0-rc.1", "1.0.0-rc.1+build.1", "1.0.0", "1.0.0+0.3.7", "1.3.7+build", "1.3.7+build.2.b8f12d7", "1.3.7+build.11.e0f985a")

val shuffled = shuffle(expected)

val parsed = (List.empty[SemVersion] /: shuffled)((a, e) =>
  Version(e) match {
    case Right(v) => v :: a
    case _ => a
  }
)

// print list of sorted versions
parsed.sorted.foreach(println)
```

Doug Tangren (softprops) 2012

[sv]: http://semver.org/
