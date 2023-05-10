package com.jarrahtechnology.kassite.tween

import scala.scalajs.js.annotation.*
import scala.concurrent.duration.*

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

@JSExportAll
final case class DeltaTween(val params: DeltaTweenParameters, val manager: TweenManager) extends Tween {
  def update(delta: Duration) = params.action(delta*timeScale)
  def start = if (!manager.manages(this)) manager.run(this)
}

@JSExportAll
final case class InterpTween(val params: InterpTweenParameters, val manager: TweenManager) extends Tween {
  private var runTime = -params.delay

  def update(delta: Duration) = {
    val notStartedPreviously = !hasStarted
    runTime = runTime + delta*timeScale
    if (hasStarted) {
      if (notStartedPreviously) params.fireStarted
      params.updateTweenWith(runTime)
      if (params.hasFinishedAfter(runTime)) stop
    }
  }
  def hasStarted = runTime>Duration(0, SECONDS)
  def progressTime = runTime
  override def stop = { params.fireFinished; super.stop }
  def restart = { runTime = -params.delay; if (!manager.manages(this)) manager.run(this); this } 
  def syncTo(other: InterpTween) = { 
    runTime = params.duration * LoopType.loopForwardFromStart(other.params.duration)(other.runTime)
    timeScale = other.timeScale
  }
}
