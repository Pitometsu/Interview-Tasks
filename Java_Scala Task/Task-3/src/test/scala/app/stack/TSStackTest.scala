package app
package stack

import org.scalatest.FunSpec

class TSStackTest extends FunSpec {
  val testStack = TSStack(4, 3, 2, 1, 0)

  describe("This is fine") {
    it("would be okay") {
      assert(
        (3 :+ 2 +: 1 +: 0 +: TSStack()).push(4)
        === testStack)
      assert(testStack.unsafePop === (4, TSStack(3, 2, 1, 0)))
      assertThrows[TSStackException] { TSStack().unsafePop }
    }
  }
}
