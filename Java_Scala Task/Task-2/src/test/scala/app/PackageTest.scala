package app

import org.scalatest.FunSuite
import org.scalatest.Matchers._

import eu.timepit.refined.refineMV

class PackageTest extends FunSuite {
  test("Domain data types requirements") {
    noException should be thrownBy refineMV[NameRefine]("Proper-User_name.1")
    assertTypeError("refineMV[NameRefine](\"@a\")")
    assertTypeError("refineMV[NameRefine](\"\")")
  }
}
