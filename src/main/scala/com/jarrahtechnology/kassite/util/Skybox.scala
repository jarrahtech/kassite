package com.jarrahtechnology.kassite

import facade.babylonjs.*
import facade.babylonjs.global.BABYLON as BABYLON_IMPL
import facade.babylonjs.anon.BackUVsBottomBaseAt

object Skybox {

  // see https://doc.babylonjs.com/features/featuresDeepDive/environment/skybox
  // or alternative method https://playground.babylonjs.com/#UXGCPG
  def create(scene: BABYLON.Scene, imagesUrl: String, size: Double) = { 
    val skybox = BABYLON_IMPL.MeshBuilder.CreateBox("skyBox", BackUVsBottomBaseAt.MutableBuilder(BackUVsBottomBaseAt()).setSize(size), scene)
    val skyboxMaterial = BABYLON_IMPL.StandardMaterial("skyBox", scene)
    skyboxMaterial.backFaceCulling = false
    skyboxMaterial.reflectionTexture = BABYLON_IMPL.CubeTexture(imagesUrl, scene, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {})
    skyboxMaterial.reflectionTexture.coordinatesMode = BABYLON_IMPL.Texture.SKYBOX_MODE
    skyboxMaterial.diffuseColor = BABYLON_IMPL.Color3(0, 0, 0)
    skyboxMaterial.specularColor = BABYLON_IMPL.Color3.Black()
    skybox.material = skyboxMaterial
  }
}
