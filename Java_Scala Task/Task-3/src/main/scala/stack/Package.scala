import scala.language.implicitConversions

package object Package {
  /**
    * Stack aka `LIFO` data structure of covariant data type.
    *
    * @tparam T the type of stack element
    */
  sealed trait TSStack[+T] {
    def push[A >: T](item: A): TSStack[A] = new TSFull(item, this)
  }
  implicit def fromList[T](seq: Seq[T]): TSStack[T] = seq match {
    case top :: rest => TSStack[T](rest: _*) push top
    case _ => TSEmpty[T]()
  }
  implicit def fromArray[T](array: Array[T]): TSStack[T] = TSStack(array.toVector: _*)
  implicit def fromVector[T](vector: Vector[T]): TSStack[T] = vector match {
    case Vector(top, rest@_*) => TSStack[T](rest: _*) push top
    case _ => TSEmpty[T]()
  }
  final case class TSEmpty[T]() extends TSStack[T]
  final case class TSFull[T](top: T, rest: TSStack[T]) extends TSStack[T] {
    lazy val pop: T = top
  }
  object TSStack {
    def apply[T](items: T*): TSStack[T] = items
  }
  type :+[T] = TSFull[T]
  final case class FSTop[+T](value: T) { // ???: private
    def :+[A >: T](rest: TSStack[A]): TSFull[A] = TSFull(value, rest)
  }
  implicit def toFSTop[T](value: T): FSTop[T] = new FSTop[T](value)
}
