package com.jarrahtechnology.kassite.tween

import scala.scalajs.js.annotation.*
import facade.babylonjs.*
import facade.babylonjs.global.BABYLON as BABYLON_IMPL
import scala.concurrent.duration.*
import com.jarrahtechnology.kassite.shader.*
import com.jarrahtechnology.util.Interpolation.lerp
import com.jarrahtechnology.kassite.util.Math.color3lerp

// Can't share materials without them all changing, except at start, see https://www.babylonjs-playground.com/#2IFRKC#251

@JSExportAll 
object MaterialTween {
  def shaderColor3Parameter(duration: Duration, mat: BABYLON.ShaderMaterial, name: String, dest: BABYLON.Color3, origin: BABYLON.Color3) = 
    InterpTweenBuilder()
      .withAction(v => mat.setColor3(name, color3lerp(v, origin, dest)))
      .withDuration(duration)
      .withLoop(LoopType.PingPongForever)
      .build()
  
  def shaderColor3ParameterFromTo(duration: Duration, mat: ParameterisedShaderMaterial, name: String, origin: BABYLON.Color3, dest: BABYLON.Color3) = 
    InterpTweenBuilder()
      .withAction(v => mat.setColor3(name, color3lerp(v, origin, dest)))
      .withDuration(duration)
      .withLoop(LoopType.PingPongForever)
      .withOnFinish(_ => mat.setColor3(name, origin))
      .build()
  def shaderColor3ParameterTo(duration: Duration, mat: ParameterisedShaderMaterial, name: String, dest: BABYLON.Color3) = 
      shaderColor3ParameterFromTo(duration, mat, name, mat.getColor3(name).getOrElse(BABYLON_IMPL.Color3(1,1,1)), dest)
  
  def shaderFloatParameterFromTo(duration: Duration, delay: Duration, mat: ParameterisedShaderMaterial, name: String, origin: Double, dest: Double, onStart: Option[InterpTweenParameters => Unit], onFinish: Option[InterpTweenParameters => Unit]) = 
    InterpTweenBuilder()
      .withAction(v => mat.setFloat(name, lerp(v, origin, dest)))
      .withDuration(duration)
      .withOnStart(onStart)
      .withOnFinish(onFinish)
      .build()
  def shaderFloatParameter(duration: Duration, delay: Duration, mat: ParameterisedShaderMaterial, name: String, dest: Double, onStart: Option[InterpTweenParameters => Unit], onFinish: Option[InterpTweenParameters => Unit]) = 
      shaderFloatParameterFromTo(duration, delay, mat, name, mat.getFloat(name).getOrElse(0d), dest, onStart, onFinish) 
}
