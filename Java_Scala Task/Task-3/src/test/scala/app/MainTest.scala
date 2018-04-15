package app

import org.scalatest.FunSpec
import cats.data.State

import app.stack._
import app.main.Main._

class MainTest extends FunSpec {
  val testStack = TSStack(4, 3, 2, 1, 0)
  val doTesting: TSState[Int, List[Int]] = for {
    p1 <- doUnsafePop()
    p2 <- doUnsafePop()
    _  <- doPush(5)
    _  <- doPush(6)
    p3 <- doUnsafePop()
  } yield List(p1, p2, p3)

  describe("This is fine") {
    it("would be okay") {
      val (stack, popped) = doTesting.run(testStack).value
      assert(stack === TSStack(5, 2, 1, 0))
      assert(popped == List(4, 3, 6))
    }
    it("may even hurt you, and break your heart") {
      val emptyStack = TSEmpty()
      assertThrows[TSStackException] { doTesting.run(emptyStack).value }
    }
  }
}

