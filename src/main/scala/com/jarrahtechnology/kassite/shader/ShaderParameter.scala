package com.jarrahtechnology.kassite.shader

import scala.scalajs.js.annotation.*
import facade.babylonjs.*
import scala.scalajs.*

@JSExportAll
enum ShaderParamType {
  case Uniform
  case Texture
}

sealed trait ShaderParameter[T](val setter: BABYLON.ShaderMaterial => (String, T) => Unit) {
  def name: String
  def paramType: ShaderParamType
  def initialValue: Option[T]

  def setInitial(mat: BABYLON.ShaderMaterial) = initialValue.foreach(setter(mat)(name, _))
}

@JSExportAll final case class Array2ShaderParameter(val name: String, val paramType: ShaderParamType, val initialValue: Option[js.Array[Double]]) extends ShaderParameter(_.setArray2)
@JSExportAll final case class Array3ShaderParameter(val name: String, val paramType: ShaderParamType, val initialValue: Option[js.Array[Double]]) extends ShaderParameter(_.setArray3)
@JSExportAll final case class Array4ShaderParameter(val name: String, val paramType: ShaderParamType, val initialValue: Option[js.Array[Double]]) extends ShaderParameter(_.setArray4)
@JSExportAll final case class Color3ShaderParameter(val name: String, val paramType: ShaderParamType, val initialValue: Option[BABYLON.Color3]) extends ShaderParameter(_.setColor3)
@JSExportAll final case class Color3ArrayShaderParameter(val name: String, val paramType: ShaderParamType, val initialValue: Option[js.Array[BABYLON.Color3]]) extends ShaderParameter(_.setColor3Array)
@JSExportAll final case class Color4ShaderParameter(val name: String, val paramType: ShaderParamType, val initialValue: Option[BABYLON.Color4]) extends ShaderParameter(_.setColor4)
@JSExportAll final case class Color4ArrayShaderParameter(val name: String, val paramType: ShaderParamType, val initialValue: Option[js.Array[BABYLON.Color4]]) extends ShaderParameter(_.setColor4Array)
@JSExportAll final case class ExternalTextureShaderParameter(val name: String, val paramType: ShaderParamType, val initialValue: Option[BABYLON.ExternalTexture]) extends ShaderParameter(_.setExternalTexture)
@JSExportAll final case class FloatShaderParameter(val name: String, val paramType: ShaderParamType, val initialValue: Option[Double]) extends ShaderParameter(_.setFloat)
@JSExportAll final case class FloatsShaderParameter(val name: String, val paramType: ShaderParamType, val initialValue: Option[js.Array[Double]]) extends ShaderParameter(_.setFloats)
@JSExportAll final case class IntShaderParameter(val name: String, val paramType: ShaderParamType, val initialValue: Option[Double]) extends ShaderParameter(_.setInt)
@JSExportAll final case class MatricesShaderParameter(val name: String, val paramType: ShaderParamType, val initialValue: Option[js.Array[BABYLON.Matrix]]) extends ShaderParameter(_.setMatrices)
@JSExportAll final case class MatrixShaderParameter(val name: String, val paramType: ShaderParamType, val initialValue: Option[BABYLON.Matrix]) extends ShaderParameter(_.setMatrix)
// TODO: how to handle the below? Same issue in ParameterisedShaderMaterial
//final case class Matrix2x2ShaderParameter(val name: String, val paramType: ShaderParamType, val initialValue: Option[js.Array[Double]]) extends ShaderParameter(_.setMatrix2x2)
//final case class TypedMatrix2x2ShaderParameter(val name: String, val paramType: ShaderParamType, val initialValue: Option[js.typedarray.Float32Array]) extends ShaderParameter(_.setMatrix2x2)
//final case class Matrix3x3ShaderParameter(val name: String, val paramType: ShaderParamType, val initialValue: Option[js.Array[Double]]) extends ShaderParameter(_.setMatrix3x3)
//final case class TypedMatrix3x3ShaderParameter(val name: String, val paramType: ShaderParamType, val initialValue: Option[js.typedarray.Float32Array]) extends ShaderParameter(_.setMatrix3x3)
@JSExportAll final case class QuaternionShaderParameter(val name: String, val paramType: ShaderParamType, val initialValue: Option[BABYLON.Quaternion]) extends ShaderParameter(_.setQuaternion)
@JSExportAll final case class QuaternionArrayShaderParameter(val name: String, val paramType: ShaderParamType, val initialValue: Option[js.Array[BABYLON.Quaternion]]) extends ShaderParameter(_.setQuaternionArray)
@JSExportAll final case class StorageBufferShaderParameter(val name: String, val paramType: ShaderParamType, val initialValue: Option[BABYLON.StorageBuffer]) extends ShaderParameter(_.setStorageBuffer)
@JSExportAll final case class TextureShaderParameter(val name: String, val paramType: ShaderParamType, val initialValue: Option[BABYLON.BaseTexture]) extends ShaderParameter(_.setTexture)
@JSExportAll final case class TextureArrayShaderParameter(val name: String, val paramType: ShaderParamType, val initialValue: Option[js.Array[BABYLON.BaseTexture]]) extends ShaderParameter(_.setTextureArray)
@JSExportAll final case class TextureSamplerShaderParameter(val name: String, val paramType: ShaderParamType, val initialValue: Option[BABYLON.TextureSampler]) extends ShaderParameter(_.setTextureSampler)
@JSExportAll final case class UIntShaderParameter(val name: String, val paramType: ShaderParamType, val initialValue: Option[Double]) extends ShaderParameter(_.setUInt) {
  require(initialValue.map(_>=0).getOrElse(true), "must be >=0")
}
@JSExportAll final case class UniformBufferShaderParameter(val name: String, val paramType: ShaderParamType, val initialValue: Option[BABYLON.UniformBuffer]) extends ShaderParameter(_.setUniformBuffer)
@JSExportAll final case class V2ShaderParameter(val name: String, val paramType: ShaderParamType, val initialValue: Option[BABYLON.Vector2]) extends ShaderParameter(_.setVector2)
@JSExportAll final case class V3ShaderParameter(val name: String, val paramType: ShaderParamType, val initialValue: Option[BABYLON.Vector3]) extends ShaderParameter(_.setVector3)
@JSExportAll final case class V4ShaderParameter(val name: String, val paramType: ShaderParamType, val initialValue: Option[BABYLON.Vector4]) extends ShaderParameter(_.setVector4)

@JSExportAll
final case class ShaderParams(params: Seq[ShaderParameter[_]]) extends Iterable[ShaderParameter[_]] {
  def iterator = params.iterator
  def union(other: ShaderParams) = ShaderParams(params ++ other.params)

  def collectNames(typ: ShaderParamType) = params.foldLeft(js.Array.apply[String]())((c, p) => if (p.paramType==typ) c:+p.name else c)
  lazy val uniformNames = collectNames(ShaderParamType.Uniform)
  lazy val textureNames = collectNames(ShaderParamType.Texture)
}

@JSExportAll
object ShaderParams {
  // TODO: extract params from shader code
  //def derive(code: String) = ???
}
