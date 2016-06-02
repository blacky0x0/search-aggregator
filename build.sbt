name := "SearchAggregator"

version := "1.0"

scalaVersion := "2.10.5"

libraryDependencies ++= Seq(
  "org.json" % "org.json" % "chargebee-1.0",
  "org.scalatest" %% "scalatest" % "1.9.1" % "test",
  "junit" % "junit" % "4.11" % "test",
  "com.novocode" % "junit-interface" % "0.11" % "test->default",
  "org.mockito" % "mockito-core" % "1.9.5"
)
