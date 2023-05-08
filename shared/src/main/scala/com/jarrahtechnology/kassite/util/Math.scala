package com.jarrahtechnology.kassite.util

import scala.scalajs.js.annotation.*
import generated.babylonjs.*
import generated.babylonjs.global.BABYLON as BABYLON_IMPL
import com.jarrahtechnology.util.Interpolation.*

@JSExportAll
object Math {

  def vector2lerp(v: Double, a: BABYLON.Vector2, b: BABYLON.Vector2) = BABYLON_IMPL.Vector2(lerp(v, a.x, b.x), lerp(v, a.y, b.y))
  def vector3lerp(v: Double, a: BABYLON.Vector3, b: BABYLON.Vector3) = BABYLON_IMPL.Vector3(lerp(v, a.x, b.x), lerp(v, a.y, b.y), lerp(v, a.z, b.z)) 
  def color3lerp(v: Double, a: BABYLON.Color3, b: BABYLON.Color3) = BABYLON_IMPL.Color3(lerp(v, a.r, b.r), lerp(v, a.g, b.g), lerp(v, a.b, b.b))
}