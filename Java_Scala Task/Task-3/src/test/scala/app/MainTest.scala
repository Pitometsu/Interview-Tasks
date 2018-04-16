package app

import org.scalatest.FunSpec
import cats.implicits._

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
      assertThrows[TSStackException] { doTesting.run(TSEmpty()).value }
    }
    describe("Infinity and beyond") {
      it("might be safe") {
        val doTesting2: ExcState[Int, List[Int]] = for {
          _  <- doPushExc(0)
          _  <- doPushExc(1)
          p1 <- doPop()
          p2 <- doPop()
          p3 <- doPop()
        } yield List(p1, p2, p3)
        doTesting2.run(TSEmpty[Int]()) match {
          case Left(exc) => println(exc.isInstanceOf[TSStackException])
          case _ =>
        }
      }
    }
  }
}

