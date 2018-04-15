package app
package main

import cats.data.State
import app.stack._

object Main extends App {
  /**
    * Type of [[TSStack]] as state
    * @tparam T the type of stack content
    * @tparam V the type of result
    */
  type TSState[T, V] = State[TSStack[T], V]
  def doUnsafePop[T](): TSState[T, T] = State(_.unsafePop.swap)
  def doPush[T](item: T): TSState[T, Unit] = State modify (_ push item)
}
