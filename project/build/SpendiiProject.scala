import sbt._
class SpendiiProject(info: ProjectInfo) extends ParentProject(info) {
  lazy val api = project("spendii-api", "Spendii API", new SubProject(_))
  lazy val persist = project("spendii-persist", "Spendii Persistence Layer", new SubProject(_), api)
  lazy val example = project("spendii-examples", "Spendii Examples", new SubProject(_), api, persist)

  class SubProject(info: ProjectInfo) extends DefaultProject(info) {
    //replace this with your own repository or remove it all-together to use global repositories.
    lazy val artifactory = "Artifactory Release" at "http://hyperion:9080/artifactory/libs-releases"
    lazy val scalatest = "org.scalatest" % "scalatest" % "1.2" % "test->default"

    lazy val maxCompileErrors = MaxCompileErrors(5)
    lazy val maxTestCompileErrors = MaxCompileErrors(3)

    override def compileOptions = CompileOption("-encoding") :: CompileOption("UTF-8") :: maxCompileErrors :: super.compileOptions.toList

    override def testCompileOptions = maxTestCompileErrors :: super.testCompileOptions.toList
  }
}
