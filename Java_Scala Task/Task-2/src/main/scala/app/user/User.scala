package app
package user

import monocle.Lens
import monocle.Prism
import monocle.macros.GenLens
import monocle.macros.syntax.lens._
import monocle.syntax.all._

import eu.timepit.refined.{ refineV, refineMV }
import eu.timepit.refined.numeric.NonNegative


sealed trait Plan
case class Free(remaining: Actions) extends Plan
case class Paid(left: Days) extends Plan

final case class User(name: Name, level: Level, experience: Exp, plan: Plan) {
  lazy val planChange: Lens[User, Plan] = GenLens[User](_.plan)

  lazy val actionsChange: Prism[Plan, Actions] = Prism.partial[Plan, Actions] {
    case Free(actions) => actions }(Free)
  lazy val daysChange: Prism[Plan, Days] = Prism.partial[Plan, Days] {
    case Paid(days) => days }(Paid)

  private def levelUp: User = {
    lazy val nextLevel: Level = {
      refineV[NonNegative](this.level.value + this.experience.value / app.level.value)
        .getOrElse(refineMV[NonNegative](0))
    }
    lazy val nextExperience: Exp = {
      refineV[NonNegative](this.experience.value % app.level.value)
        .getOrElse(refineMV[NonNegative](0))
    }

    this.lens(_.experience).set(nextExperience)
      .lens(_.level).set(nextLevel)
  }

  def refresh: User = ((this.levelUp
    &|-> planChange
    ^<-? actionsChange).modify(app.refresh)
    &|-> planChange
    ^<-? daysChange).modify(decrement)

  def action: User = (this
    &|-> planChange
    ^<-? actionsChange).modify(decrement)
}
