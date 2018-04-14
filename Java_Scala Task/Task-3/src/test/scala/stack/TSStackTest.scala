package stack

import org.scalatest.FunSpec

class TSStackTest extends FunSpec {
  describe("This is fine") {
    it("would be okay") {
      assert(
        (3 :+ 2 +: 1 +: 0 +: TSStack()).push(4)
        === TSStack(4, 3, 2, 1, 0))
      assert(TSStack(4, 3, 2, 1, 0).pop === 4)
      assertThrows[TSStackException] { TSStack().pop }
    }
  }
}
