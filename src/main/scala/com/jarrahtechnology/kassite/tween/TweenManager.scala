package com.jarrahtechnology.kassite.tween

import scala.scalajs.js.annotation.*
import scala.concurrent.duration.*
import scala.collection.mutable.HashSet
import facade.babylonjs.*

@JSExportAll
final class TweenManager(scene: BABYLON.Scene, deltaFn: BABYLON.Scene => Duration) {
  private val tweens: HashSet[Tween] = HashSet.empty[Tween]
  private var timeScale = 1d

  scene.onBeforeRenderObservable.add((sc, ev) => tweens.foreach(_.update(deltaFn(scene))))

  def run(t: Tween) = if (t.manager == this) tweens.add(t)
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