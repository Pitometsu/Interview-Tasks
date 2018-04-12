package main

import org.scalatest.FunSuite

import Main.frequencies

class MainTest extends FunSuite {
  val pattern : String = "aAabcbz"

  test("Task-1") {
    assert(frequencies(pattern) === List[Double](Array(3.0/7, 2.0/7, 1.0/7)
      ++ Array.fill(('a' to 'z').length - 4)(0.0)
      ++ Array(1.0/7) : _*))
  }
}
