package com.jarrahtechnology.kassite.tween

import scala.scalajs.js.annotation.*
import facade.babylonjs.*
import scala.concurrent.duration.*
import com.jarrahtechnology.kassite.util.Math.*

@JSExportAll
final case class ScaleTweenParameters(d: Duration, val mesh: BABYLON.Mesh, val dest: BABYLON.Vector3, val origin: BABYLON.Vector3, onFinished: Option[ScaleTweenParameters => Unit]) 
  extends TweenParameters[ScaleTweenParameters](d, v => mesh.scaling = vector3lerp(v, origin, dest), LoopType.Once, EaseType.Sigmoid, Duration.Zero, None, onFinished) 

@JSExportAll
object ScaleTween {
  def scaleTo(duration: Duration, mesh: BABYLON.Mesh, dest: BABYLON.Vector3, onFinished: Option[ScaleTweenParameters => Unit]) = 
    ScaleTweenParameters(duration, mesh, dest, mesh.scaling, onFinished)
}
