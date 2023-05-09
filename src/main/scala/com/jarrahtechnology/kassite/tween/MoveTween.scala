package com.jarrahtechnology.kassite.tween

import scala.scalajs.js.annotation.*
import typings.babylonjs.*
import scala.concurrent.duration.*
import com.jarrahtechnology.kassite.util.Math.*

@JSExportAll
final case class LineMoveTweenParameters(d: Duration, val mesh: BABYLON.Mesh, val dest: BABYLON.Vector3, val origin: BABYLON.Vector3) 
  extends TweenParameters[LineMoveTweenParameters](d, v => mesh.position = vector3lerp(v, origin, dest), LoopType.Once, EaseType.Sigmoid, Duration.Zero, None, None) 

@JSExportAll
object MoveTween {
  def moveTo(duration: Duration, mesh: BABYLON.Mesh, dest: BABYLON.Vector3) =  moveFromTo(duration, mesh, dest, mesh.position)
  def moveFrom(duration: Duration, mesh: BABYLON.Mesh, origin: BABYLON.Vector3) = moveFromTo(duration, mesh, mesh.position, origin)
  def moveFromTo(duration: Duration, mesh: BABYLON.Mesh, dest: BABYLON.Vector3, origin: BABYLON.Vector3) = 
    LineMoveTweenParameters(duration, mesh, dest, origin)

}