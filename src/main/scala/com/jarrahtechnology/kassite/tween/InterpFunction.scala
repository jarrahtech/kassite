package com.jarrahtechnology.kassite.tween

import scala.scalajs.js.annotation.*
import scala.concurrent.duration.*
import facade.babylonjs.*
import com.jarrahtechnology.kassite.shader.ParameterisedShaderMaterial
import com.jarrahtechnology.util.Interpolation.lerp
import com.jarrahtechnology.kassite.util.Math.{vector3lerp, color3lerp}

@JSExportAll
object InterpFunction {
    
  def scale(tweenTarget: BABYLON.Mesh, start: BABYLON.Vector3, end: BABYLON.Vector3): Double => Unit = 
    progressPercent => tweenTarget.scaling = vector3lerp(progressPercent, start, end)

  def move(tweenTarget: BABYLON.Mesh, start: BABYLON.Vector3, end: BABYLON.Vector3): Double => Unit = 
    progressPercent => tweenTarget.position = vector3lerp(progressPercent, start, end)

  def shaderParam(tweenTarget: ParameterisedShaderMaterial, param: String, start: BABYLON.Color3, end: BABYLON.Color3): Double => Unit = 
    progressPercent => tweenTarget.setColor3(param, color3lerp(progressPercent, start, end))
  def shaderParam(tweenTarget: ParameterisedShaderMaterial, param: String, start: Double, end: Double): Double => Unit = 
    progressPercent => tweenTarget.setFloat(param, lerp(progressPercent, start, end))
}
