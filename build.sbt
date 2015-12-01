Nice.scalaProject

name := "tophat"
organization := "ohnosequences-bundles"
description := "A bundle for tophat tool"

publishBucketSuffix := "era7.com"

resolvers ++= Seq(
  "Era7 public maven releases"  at s3("releases.era7.com").toHttps(s3region.value.toString),
  "Era7 public maven snapshots" at s3("snapshots.era7.com").toHttps(s3region.value.toString)
)

libraryDependencies ++= Seq (
  "ohnosequences"         %% "statika" % "2.0.0-M4",
  "ohnosequences-bundles" %% "bowtie2" % "0.2.0-SNAPSHOT"
)
