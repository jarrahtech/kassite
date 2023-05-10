package com.jarrahtechnology.kassite.tween

import scala.scalajs.js.annotation.*
import facade.babylonjs.*
import scala.concurrent.duration.*
import com.jarrahtechnology.kassite.util.Math.*

@JSExportAll
object ScaleTween {
  
  def scaleFromTo(duration: Duration, mesh: BABYLON.Mesh, origin: BABYLON.Vector3, dest: BABYLON.Vector3, onFinished: Option[InterpTweenParameters => Unit]) = 
    InterpTweenBuilder()
      .withAction(v => mesh.scaling = vector3lerp(v, origin, dest))
      .withDuration(duration)
      .withOnFinish(onFinished)
      .build()
  
  def scaleTo(duration: Duration, mesh: BABYLON.Mesh, dest: BABYLON.Vector3, onFinished: Option[InterpTweenParameters => Unit]) = 
    scaleFromTo(duration, mesh, mesh.scaling, dest, onFinished)
}
