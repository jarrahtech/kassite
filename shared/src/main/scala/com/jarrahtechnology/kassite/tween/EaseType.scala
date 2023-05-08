package com.jarrahtechnology.kassite.tween

import scala.scalajs.js.annotation.*
import com.jarrahtechnology.util.*
import com.jarrahtechnology.util.Interpolation.*

@JSExportAll
trait EaseType(val method: Interpolator)

@JSExportAll
final case class EaseCurve(curve: InterpCurve) extends EaseType(curve.interp) {
  require(curve.isUnit, "Must be unit curve")
}

@JSExportAll
object EaseType {
  def reverse(other: Interpolator): Interpolator = v => 1-other(1-v)
  def inOut(in: Interpolator): Interpolator = v => if (v<=0.5d) in(v*2d)/2d else (reverse(in)(v*2d-1d)+1d)/2d
  def outIn(in: Interpolator): Interpolator = v => if (v<=0.5d) reverse(in)(v*2d)/2d else (in(v*2d-1d)+1d)/2d

  case object Linear extends EaseType(identity)
  case object Spring extends EaseType(springInterp)
  case object OutInSpring extends EaseType(outIn(Spring.method))

  case object Sigmoid extends EaseType(eInterp(0.2))
  case object SteepSigmoid extends EaseType(eInterp(0.1))

  case object InQuad extends EaseType(powerInterp(2))
  case object OutQuad extends EaseType(reverse(InQuad.method))
  case object InOutQuad extends EaseType(inOut(InQuad.method))
  case object OutInQuad extends EaseType(outIn(InQuad.method))

  case object InCubic extends EaseType(powerInterp(3))
  case object OutCubic extends EaseType(reverse(InCubic.method))

  case object InQuart extends EaseType(powerInterp(4))
  case object OutQuart extends EaseType(reverse(InQuart.method))
  case object InOutQuart extends EaseType(inOut(InQuart.method))

  case object InSin extends EaseType(sinInterp)
  case object OutSin extends EaseType(reverse(InSin.method))

  case object InBounce extends EaseType(bounceInterp)
  case object OutBounce extends EaseType(reverse(InBounce.method))
}
