package com.jarrahtechnology.kassite.util

import scala.scalajs.js.annotation.*
import facade.babylonjs.*
import facade.babylonjs.global.BABYLON as BABYLON_IMPL
import com.jarrahtechnology.util as JT

@JSExportAll
object VectorConvert {
    def toV2(v: BABYLON.Vector2) = JT.Vector2(v.x, v.y)
    def toV2(v: JT.Vector2) = BABYLON_IMPL.Vector2(v.x, v.y)

    def toV2Flat(v: BABYLON.Vector3) = JT.Vector2(v.x, v.y)
    def toV2Flat(v: JT.Vector3) = BABYLON_IMPL.Vector2(v.x, v.y)

    def toV3(v: BABYLON.Vector3) = JT.Vector3(v.x, v.y, v.z)
    def toV3(v: JT.Vector3) = BABYLON_IMPL.Vector3(v.x, v.y, v.z)

    def toV3Flat(v: BABYLON.Vector2): JT.Vector3 = toV3(v, 0d)
    def toV3Flat(v: JT.Vector2): BABYLON.Vector3 = toV3(v, 0d)
    def toV3(v: BABYLON.Vector2, z: Double) = JT.Vector3(v.x, v.y, z)
    def toV3(v: JT.Vector2, z: Double) = BABYLON_IMPL.Vector3(v.x, v.y, z)
}
