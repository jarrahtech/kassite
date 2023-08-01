package com.jarrahtechnology.kassite.controls

import facade.babylonjs.*
import facade.babylonjs.global.BABYLON as BABYLON_IMPL

// TODO: generic modifiers? store currently down keys and check/pass
sealed trait InputDef

sealed trait ModifiedInputDef(alt: Boolean, ctrl: Boolean, meta: Boolean, shift: Boolean) extends InputDef
sealed trait ModifiedMouseBuilder {
  def from(ev: BABYLON.IMouseEvent): ModifiedInputDef
}

final case class KeyboardInputDef(val key: String, val alt: Boolean, val ctrl: Boolean, val meta: Boolean, val shift: Boolean) extends ModifiedInputDef(alt, ctrl, meta, shift)
object KeyboardInputDef {
  def from(ev: BABYLON.IKeyboardEvent) = KeyboardInputDef(ev.key, ev.altKey, ev.ctrlKey, ev.metaKey, ev.shiftKey)
  def from(key: String) = KeyboardInputDef(key, false, false, false, false)
}

final case class MouseButtonInputDef(val num: Int, val alt: Boolean, val ctrl: Boolean, val meta: Boolean, val shift: Boolean) extends ModifiedInputDef(alt, ctrl, meta, shift)
object MouseButtonInputDef extends ModifiedMouseBuilder {
  def from(ev: BABYLON.IMouseEvent) = MouseButtonInputDef(ev.button.toInt, ev.altKey, ev.ctrlKey, ev.metaKey, ev.shiftKey)
  def from(button: Int) = MouseButtonInputDef(button, false, false, false, false)
}
final case class MousePositionInputDef(val alt: Boolean, val ctrl: Boolean, val meta: Boolean, val shift: Boolean) extends ModifiedInputDef(alt, ctrl, meta, shift)
object MousePositionInputDef extends ModifiedMouseBuilder {
  def from(ev: BABYLON.IMouseEvent) = MousePositionInputDef(ev.altKey, ev.ctrlKey, ev.metaKey, ev.shiftKey)
  def clean = MousePositionInputDef(false, false, false, false)
}
final case class MouseWheelInputDef(val alt: Boolean, val ctrl: Boolean, val meta: Boolean, val shift: Boolean) extends ModifiedInputDef(alt, ctrl, meta, shift)
object MouseWheelInputDef extends ModifiedMouseBuilder {
  def from(ev: BABYLON.IMouseEvent) = MouseWheelInputDef(ev.altKey, ev.ctrlKey, ev.metaKey, ev.shiftKey)
  def clean = MouseWheelInputDef(false, false, false, false)
}

object InputDef {
  val one = KeyboardInputDef.from("1")
  val two = KeyboardInputDef.from("2")
  val three = KeyboardInputDef.from("3")
  val four = KeyboardInputDef.from("4")
  val five = KeyboardInputDef.from("5")
  val six = KeyboardInputDef.from("6")
  val seven = KeyboardInputDef.from("7")
  val eight = KeyboardInputDef.from("8")
  val nine = KeyboardInputDef.from("9")
  val zero = KeyboardInputDef.from("0")

  val a = KeyboardInputDef.from("a")
  val b = KeyboardInputDef.from("b")
  val c = KeyboardInputDef.from("c")
  val d = KeyboardInputDef.from("d")
  val e = KeyboardInputDef.from("e")
  val f = KeyboardInputDef.from("f")
  val g = KeyboardInputDef.from("g")
  val h = KeyboardInputDef.from("h")
  val i = KeyboardInputDef.from("i")
  val j = KeyboardInputDef.from("j")
  val k = KeyboardInputDef.from("k")
  val l = KeyboardInputDef.from("l")
  val m = KeyboardInputDef.from("m")
  val n = KeyboardInputDef.from("n")
  val o = KeyboardInputDef.from("o")
  val p = KeyboardInputDef.from("p")
  val q = KeyboardInputDef.from("q")
  val r = KeyboardInputDef.from("r")
  val s = KeyboardInputDef.from("s")
  val t = KeyboardInputDef.from("t")
  val u = KeyboardInputDef.from("u")
  val v = KeyboardInputDef.from("v")
  val w = KeyboardInputDef.from("w")
  val x = KeyboardInputDef.from("x")
  val y = KeyboardInputDef.from("y")
  val z = KeyboardInputDef.from("z")

  val arrowLeft = KeyboardInputDef.from("ArrowLeft")
  val arrowRight = KeyboardInputDef.from("ArrowRight")
  val arrowUp = KeyboardInputDef.from("ArrowUp")
  val arrowDown = KeyboardInputDef.from("ArrowDown")
  val esc = KeyboardInputDef.from("Escape")

  val mouse0 = MouseButtonInputDef.from(0)
  val mouseLeft = mouse0
  val mouse1 = MouseButtonInputDef.from(1)
  val mouse2 = MouseButtonInputDef.from(2)
  val mousePosition = MousePositionInputDef.clean
  val mouseWheel = MouseWheelInputDef.clean
}

// TODO: gamepad buttons
