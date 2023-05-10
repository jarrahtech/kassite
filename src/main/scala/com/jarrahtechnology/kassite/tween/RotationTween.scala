package com.jarrahtechnology.kassite.tween

import scala.scalajs.js.annotation.*
import facade.babylonjs.*
import scala.concurrent.duration.*

@JSExportAll
object RotationTween {
  def rotateAround(duration: Duration, mesh: BABYLON.AbstractMesh, axis: BABYLON.Vector3) = 
    DeltaTweenParameters(v => mesh.rotate(axis, v.toMicros*2*math.Pi/duration.toMicros))
}
