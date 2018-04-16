package app
package main

import scala.language.higherKinds

import cats.data.{State, StateT}
import cats.Eval
import cats.instances.either._

import app.stack._
import app.stack.TSStackException

object Main extends App {
  /**
    * Type of [[TSStack]] as a state.
    *
    * @tparam F the context of state
    * @tparam T the type of stack content
    * @tparam V the type of result
    */
  type TSStateT[F[_], T, V] = StateT[F, TSStack[T], V]
  type TSState[T, V] = TSStateT[Eval, T, V]
  /**
    *  May contain [[TSStackException]] exception.
    * @tparam T the result
    */
  type TSExceptionable[T] = Either[TSStackException, T]
  type ExcState[T, V] = TSStateT[TSExceptionable, T, V]

  def doUnsafePop[T](): TSState[T, T] = State(_.unsafePop.swap)
  def doPop[T](): StateT[TSExceptionable, TSStack[T], T] = {
    def pop(stack: TSStack[T]) = try {
      Right(stack.unsafePop.swap)
    } catch {
      case exc: TSStackException => Left(exc)
    }
    StateT[TSExceptionable, TSStack[T], T](pop)
  }
  def doPush[T](item: T): TSState[T, Unit] = State.modify(_ push item)
  def doPushExc[T](item: T): ExcState[T, Unit] = StateT.modify[TSExceptionable, TSStack[T]](_ push item)
}
