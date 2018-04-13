import eu.timepit.refined._
import eu.timepit.refined.auto._
import eu.timepit.refined.numeric._
import eu.timepit.refined.api.Refined
import eu.timepit.refined.boolean._
import eu.timepit.refined.string._

package object app {
  /**
    * Limitation by `[a-z][A-Z][0-9]-._` characters.
    */
  type NameRefine = MatchesRegex[W.`"([0-9a-zA-Z_.-])+"`.T]
  /**
    * `String` limited by `NameRefine`.
    */
  type Name = String Refined NameRefine

  type UInt = Int Refined NonNegative
  type Level = UInt
  type Exp = UInt
  type Actions = UInt
  type Days = UInt

  val level: Exp = 500
  val limit: Actions = 3

  def decrement(number: UInt): UInt = {
    refineV[NonNegative](number.value - 1 max 0).getOrElse(number)
  }
  def refresh(number: UInt): UInt = {
    refineV[NonNegative](number.value max limit.value).getOrElse(number)
  }
}
