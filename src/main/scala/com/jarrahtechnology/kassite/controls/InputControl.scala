package com.jarrahtechnology.kassite.controls

import facade.babylonjs.*
import facade.babylonjs.global.BABYLON as BABYLON_IMPL
import com.jarrahtechnology.util.Subject
import scalajs.js.Date

trait InfoConvertor[T] {
  def convert(i: BABYLON.KeyboardInfo) = Option.empty[T]
  def convert(i: BABYLON.PointerInfo) = Option.empty[T]
}
object InfoConvertor {
  val empty = new InfoConvertor[Any] {}
}

sealed trait InputControl[T] {
  def name: String
  def convertor: InfoConvertor[T]
  def editable: Boolean

  def start(info: Option[T]): Unit
  def end(info: Option[T]): Unit = { } 
}

sealed trait PressInputControl[T] extends InputControl[T] {
  val onPress = Subject[(String, Option[T])]()

  def lastPressDown: Date

  def pressDown = start
  def pressUp = end
}

final case class BinaryInputControl[T](val name: String, val convertor: InfoConvertor[T] = InfoConvertor.empty, val editable: Boolean = true) extends PressInputControl[T] {
  val lastPressDown = new Date(0)

  def start(info: Option[T]) = { onPress.notify((name, info)); lastPressDown.setTime(Date.now) }
}

final case class OnceInputControl[T](val name: String, val convertor: InfoConvertor[T] = InfoConvertor.empty, val editable: Boolean = true) extends PressInputControl[T] {
  val onPressEnd = Subject[(String, Option[T])]()

  val lastPressDown = new Date(0)
  val lastPressUp = new Date(0)

  def pressNotOngoing() = lastPressDown.getTime>lastPressUp.getTime
  def start(info: Option[T]) = if (lastPressDown.getTime<=lastPressUp.getTime) { onPress.notify((name, info)); lastPressDown.setTime(Date.now) }
  override def end(info: Option[T]) = { lastPressUp.setTime(Date.now); onPressEnd.notify((name, info)) }
}

final case class ExtendedInputControl[T](val name: String, val convertor: InfoConvertor[T] = InfoConvertor.empty, val doubleThreshold: Double = 0d, val editable: Boolean = true) extends PressInputControl[T] {
  val onPressHold = Subject[(String, Option[T])]()
  val onPressEnd = Subject[(String, Option[T])]()
  val onDoublePress = Subject[(String, Option[T])]()

  val lastPressDown = new Date(0)
  val lastPressUp = new Date(0)

  def pressOngoing() = lastPressDown.getTime>lastPressUp.getTime
  def start(info: Option[T]) = if (pressOngoing()) { onPressHold.notify((name, info)) }
    else {
      val now = Date.now
      if ((now-lastPressDown.getTime)>doubleThreshold) then onPress.notify((name, info)) else onDoublePress.notify((name, info)) 
      lastPressDown.setTime(now)
    }
  override def end(info: Option[T]) = { lastPressUp.setTime(Date.now); onPressEnd.notify((name, info)) }
}
