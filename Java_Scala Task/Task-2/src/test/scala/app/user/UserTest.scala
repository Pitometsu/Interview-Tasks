package app
package user

import org.scalatest.FunSuite
import eu.timepit.refined.refineMV
import eu.timepit.refined.numeric.NonNegative

class UserTest extends FunSuite {
  lazy val testFreeUser = User(
    refineMV[NameRefine]("John_Doe"),
    refineMV[NonNegative](1),
    refineMV[NonNegative](1600),
    Free(refineMV[NonNegative](2)))
  lazy val testPaid1User = User(
    refineMV[NameRefine]("John_Doe"),
    refineMV[NonNegative](0),
    refineMV[NonNegative](200),
    Paid(refineMV[NonNegative](15)))
  lazy val testPaid2User = User(
    refineMV[NameRefine]("John_Doe"),
    refineMV[NonNegative](0),
    refineMV[NonNegative](200),
    Paid(refineMV[NonNegative](0)))

  test("Refresh class User") {
    assert(testFreeUser.refresh
      === User(
      refineMV[NameRefine]("John_Doe"),
      refineMV[NonNegative](4),
      refineMV[NonNegative](100),
      Free(limit)))
    assert(testFreeUser.action.action.action.action.refresh.action.action
      === User(
      refineMV[NameRefine]("John_Doe"),
      refineMV[NonNegative](4),
      refineMV[NonNegative](100),
      Free(refineMV[NonNegative](1))))
    assert(testPaid1User.refresh
      === User(
      refineMV[NameRefine]("John_Doe"),
      refineMV[NonNegative](0),
      refineMV[NonNegative](200),
      Paid(refineMV[NonNegative](14))))
    assert(testPaid2User.refresh
      === User(
      refineMV[NameRefine]("John_Doe"),
      refineMV[NonNegative](0),
      refineMV[NonNegative](200),
      Paid(refineMV[NonNegative](0)))) // ???: Shouldn't be Free here by common sense
  }
}
