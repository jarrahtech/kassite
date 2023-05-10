package com.jarrahtechnology.kassite.tween

import scala.scalajs.js.annotation.*
import scala.concurrent.duration.*
import facade.babylonjs.*

@JSExportAll
object DeltaFunction {
    
  def rotateAround(tweenTarget: BABYLON.AbstractMesh, singleRotationTime: Duration, axis: BABYLON.Vector3): Duration => Unit = 
    delta => tweenTarget.rotate(axis, delta.div(singleRotationTime)*2*math.Pi)
}
