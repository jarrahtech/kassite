package com.jarrahtechnology.kassite.tween

import scala.scalajs.js.annotation.*
import com.jarrahtechnology.util.Math.*
import facade.babylonjs.*
import scala.collection.mutable.HashSet
import scala.concurrent.duration.*
import scala.util.chaining._

trait ZZZ {
  def action: Double => Unit
  def runOn(manager: TweenManager): Tween
}

@JSExportAll
// TODO: think about delay end and  builder
trait TweenParameters[T <: TweenParameters[_]](val duration: Duration, val action: Double => Unit, val loop: LoopType, val ease: EaseType, val delay: Duration, val onStart: Option[T => Unit], val onFinish: Option[T => Unit]) 
{ //extends ZZZ {
  require(duration>Duration.Zero, s"duration=${duration} !> 0")

  val updateTweenWith = loop.progress(duration) andThen clamp01 andThen ease.method andThen action
  val hasFinishedAfter = loop.hasFinished(duration)
  def fireFinished = onFinish.foreach(_(this.asInstanceOf[T]))
  def fireStarted = onStart.foreach(_(this.asInstanceOf[T]))
  def runOn(manager: TweenManager) = InterpTween(this, manager).tap(manager.run)
}

//trait DeltaTweenParameters() extends ZZZ {
//  def runOn(manager: TweenManager) = DeltaTween(this, manager).tap(manager.run)
//}

trait Tween {
  protected var timeScale = 1d

  def manager: TweenManager
  def update(delta: Duration): Unit

  def pause = timeScale = 0
  def unpause = timeScale = 1
  def isPaused = timeScale==0 || manager.isPaused 
  def isStopped = !manager.manages(this)
  def stop: Boolean = manager.remove(this)
  def setTimeScale(s: Double) = { require(s>=0, s"speed=${s} !>= 0"); timeScale = s }
  def getTimeScale = timeScale
}

// TODO: check traits do not use parameter lists, but defs instead!!!! check all libs
// TODO: can this be subsumed by Tween? If not generalise better (names/functions), have runOn
@JSExportAll
final case class DeltaTween(action: Duration => Unit, val manager: TweenManager) extends Tween {
  def update(delta: Duration) = action(delta*timeScale)
  def start = if (!manager.manages(this)) manager.run(this)
}

@JSExportAll
final case class InterpTween(val params: TweenParameters[_], val manager: TweenManager) extends Tween {
  private var runTime = -params.delay

  def update(delta: Duration) = {
    val startTime = runTime
    runTime = runTime + delta*timeScale
    if (startTime.toNanos<=0 && runTime.toNanos>0) params.fireStarted
    params.updateTweenWith(runTime)
    if (params.hasFinishedAfter(runTime)) stop
  }
  def progressTime = runTime
  override def stop = { params.fireFinished; super.stop }
  def restart = { runTime = -params.delay; if (!manager.manages(this)) manager.run(this); this } 
  def syncTo(other: InterpTween) = { 
    runTime = params.duration * LoopType.loopForwardFromStart(other.params.duration)(other.runTime)
    timeScale = other.timeScale
  }
}

@JSExportAll
final class TweenManager(scene: BABYLON.Scene, deltaFn: BABYLON.Scene => Duration) {
  private val tweens: HashSet[Tween] = HashSet.empty[Tween]
  private var timeScale = 1d

  scene.onBeforeRenderObservable.add((sc, ev) => tweens.foreach(_.update(deltaFn(scene))))

  // Could do the next two lines in one with generics, but scala-js doesn't seem to like that at runtime :(
  def run(t: InterpTween) = if (t.manager == this) tweens.add(t)
  def run(t: DeltaTween): DeltaTween = { if (t.manager == this) tweens.add(t); t } 
  
  def remove(tween: Tween) = tweens.remove(tween)
  def manages(tween: Tween) = tweens.exists(_ == tween)

  def setTimeScale(s: Double) = { require(timeScale>=0, s"speed=${timeScale} !>= 0"); timeScale = s }
  def getTimeScale = timeScale
  def pause = timeScale = 0
  def unpause = timeScale = 1
  def isPaused = timeScale==0
}

object TweenManager {
  def frameTime(scene: BABYLON.Scene) = TweenManager(scene, s => Duration((s.getDeterministicFrameTime()*1000).toLong, MICROSECONDS))
}
