import scala.language.implicitConversions

package object stack {
  /**
    * Stack aka `LIFO` data structure of covariant data type.
    *
    * @tparam T the type of stack element
    */
  sealed trait TSStack[+T] {
    def +:[A >: T]: A => TSFull[A] = push[A]
    def push[A >: T](item: A): TSFull[A] = TSFull(item, this)
    val pop: T
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
  class TSStackException(msg: String, cause: Option[Throwable] = None) extends Exception(msg, cause.orNull)

  final case class TSEmpty[T]() extends TSStack[T] {
    lazy val pop: T = {
      throw new TSStackException("Nothing left here, stack is empty like your life...")
    }
  }
  final case class TSFull[T](top: T, rest: TSStack[T]) extends TSStack[T] {
    lazy val pop: T = top
  }
  object TSStack {
    def apply[T](items: T*): TSStack[T] = fromSeq(items)
  }
  type +:[T] = TSFull[T]
  final case class FSTop[+T](item: T) { // ???: private
    def :+[A >: T](rest: TSStack[A]): TSFull[A] = TSFull(item, rest)
  }
  implicit def toFSTop[T](value: T): FSTop[T] = new FSTop[T](value)
}
