package app

import scala.language.implicitConversions

package object stack {
  /**
    * Stack aka `LIFO` data structure of covariant data type.
    *
    * @tparam T the type of stack element
    */
  sealed trait TSStack[+T] {
    /** Synonym for [[push]]. */
    def +:[A >: T]: A => TSFull[A] = push[A]

    /**
      * Push an item into the stack.
      * @param item to push
      * @tparam A the type of item match stack content type
      * @return a new stack with the new item.
      */
    def push[A >: T](item: A): TSFull[A] = TSFull(item, this)
    /**
      * Pop a top item from the stack.
      * @throws TSStackException if stack is `TSEmpty`
      * @return a new stack without the new item.
      */
    def unsafePop: (T, TSStack[T])
  }
  implicit def fromSeq[T](seq: Seq[T]): TSStack[T] = seq match {
    case Seq(top, rest@_*) => fromSeq(rest) push top
    case _ => TSEmpty[T]()
  }
  implicit def fromArray[T](array: Array[T]): TSStack[T] = TSStack(array.toVector: _*)
  implicit def fromVector[T](vector: Vector[T]): TSStack[T] = vector match {
    case Vector(top, rest@_*) => fromSeq(rest) push top
    case _ => TSEmpty[T]()
  }
  class TSStackException(msg: String, cause: Option[Throwable] = None)
    extends Exception(msg, cause.orNull)

  final case class TSEmpty[T]() extends TSStack[T] {
    def unsafePop: (T, TSEmpty[T]) = {
      val message = "Nothing left here, stack is empty like a life without State Monad..."
      throw new TSStackException(message)
    }
  }
  final case class TSFull[T](top: T, rest: TSStack[T]) extends TSStack[T] {
    def unsafePop: (T, TSStack[T]) = pop
    def pop: (T, TSStack[T]) = (top, rest)
  }
  object TSStack {
    def apply[T](items: T*): TSStack[T] = fromSeq(items)
  }
  type +:[T] = TSFull[T]
  private[stack] final case class FSTop[+T](item: T) {
    def :+[A >: T](rest: TSStack[A]): TSFull[A] = TSFull(item, rest)
  }
  implicit def toFSTop[T](value: T): FSTop[T] = new FSTop[T](value)
}
