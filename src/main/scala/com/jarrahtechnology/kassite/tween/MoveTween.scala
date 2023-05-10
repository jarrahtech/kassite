package com.jarrahtechnology.kassite.tween

import scala.scalajs.js.annotation.*
import facade.babylonjs.*
import scala.concurrent.duration.*
import com.jarrahtechnology.kassite.util.Math.*

@JSExportAll
object MoveTween {
  def moveTo(duration: Duration, mesh: BABYLON.Mesh, dest: BABYLON.Vector3) =  moveFromTo(duration, mesh, dest, mesh.position)
  def moveFrom(duration: Duration, mesh: BABYLON.Mesh, origin: BABYLON.Vector3) = moveFromTo(duration, mesh, mesh.position, origin)
  def moveFromTo(duration: Duration, mesh: BABYLON.Mesh, dest: BABYLON.Vector3, origin: BABYLON.Vector3) = 
    InterpTweenBuilder()
      .withAction(v => mesh.position = vector3lerp(v, origin, dest))
      .withDuration(duration)
      .build()
}