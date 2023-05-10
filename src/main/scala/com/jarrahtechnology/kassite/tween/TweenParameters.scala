package com.jarrahtechnology.kassite.tween

import scala.scalajs.js.annotation.*
import scala.concurrent.duration.*
import scala.util.chaining._
import com.jarrahtechnology.util.Math.*

trait TweenParameters {
  def runOn(manager: TweenManager): Tween
}

@JSExportAll
final case class InterpTweenParameters(val action: Double => Unit, val duration: Duration, val loop: LoopType, val ease: EaseType, val delay: Duration, val onStart: Option[InterpTweenParameters => Unit], val onFinish: Option[InterpTweenParameters => Unit]) extends TweenParameters {
  require(duration>Duration.Zero, s"duration=${duration} !> 0")

  val updateTweenWith = loop.progress(duration) andThen clamp01 andThen ease.method andThen action
  val hasFinishedAfter = loop.hasFinished(duration)
  def fireFinished = onFinish.foreach(_(this))
  def fireStarted = onStart.foreach(_(this))
  def runOn(manager: TweenManager) = InterpTween(this, manager).tap(manager.run)
}

final case class InterpTweenBuilder (
  action: Double => Unit = _ => {},
  duration: Duration = Duration(1, SECONDS),
  loop: LoopType = LoopType.Once,
  ease: EaseType = EaseType.Sigmoid,
  delay: Duration = Duration(0, NANOSECONDS),
  onStart: Option[InterpTweenParameters => Unit] = None,
  onFinish: Option[InterpTweenParameters => Unit] = None
)  {
  def withAction(action: Double => Unit): InterpTweenBuilder = copy(action = action)
  def withDuration(duration: Duration): InterpTweenBuilder = copy(duration = duration)
  def withLoop(loop: LoopType): InterpTweenBuilder = copy(loop = loop)
  def withEase(ease: EaseType): InterpTweenBuilder = copy(ease = ease)
  def withDelay(delay: Duration): InterpTweenBuilder = copy(delay = delay)

  def withOnStart(onStart: Option[InterpTweenParameters => Unit]): InterpTweenBuilder = copy(onStart = onStart)
  def withOnStart(onStart: InterpTweenParameters => Unit): InterpTweenBuilder = withOnStart(Some(onStart))

  def withOnFinish(onFinish: Option[InterpTweenParameters => Unit]): InterpTweenBuilder = copy(onFinish = onFinish)
  def withOnFinish(onFinish: InterpTweenParameters => Unit): InterpTweenBuilder = withOnFinish(Some(onFinish))

  def build() = InterpTweenParameters(action, duration, loop, ease, delay, onStart, onFinish)
}

final case class DeltaTweenParameters(val action: Duration => Unit) extends TweenParameters {
  def runOn(manager: TweenManager) = DeltaTween(this, manager).tap(manager.run)
}
