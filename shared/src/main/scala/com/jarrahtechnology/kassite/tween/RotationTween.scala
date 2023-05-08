package com.jarrahtechnology.kassite.tween

import scala.scalajs.js.annotation.*
import typings.babylonjs.*
import scala.concurrent.duration.*

@JSExportAll
final case class RotateAroundTweenParameters(d: Duration, val mesh: typings.babylonjs.BABYLON.AbstractMesh, val axis: BABYLON.Vector3) 
  extends TweenParameters[RotateAroundTweenParameters](d, v => mesh.rotate(axis, 2*math.Pi/v), LoopType.CycleForever, EaseType.Linear, Duration.Zero, None, None) 

@JSExportAll
object RotationTween {
  def rotateAround(duration: Duration, mesh: typings.babylonjs.BABYLON.AbstractMesh, axis: BABYLON.Vector3, mgr: TweenManager) = 
    DeltaProgrammaticAnimation(v => mesh.rotate(axis, v.toMicros*2*math.Pi/duration.toMicros), mgr)
}
