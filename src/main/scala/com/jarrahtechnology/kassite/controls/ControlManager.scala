package com.jarrahtechnology.kassite.controls

import util.chaining.scalaUtilChainingOps
import facade.babylonjs.*
import facade.babylonjs.global.BABYLON as BABYLON_IMPL
import com.jarrahtechnology.kassite.tooltips.TooltipManager

final class ControlManager() {
  private val inputs = collection.mutable.HashMap.empty[InputDef, String]
  private val controls = collection.mutable.HashMap.empty[String, InputControl[?]]
  private var detachfn = Option.empty[() => Unit]
  var tooltipMgr = Option.empty[TooltipManager]

  def addControl(ctrls: InputControl[?]*) = ctrls.map(c => controls.addOne(c.name -> c))
  def forceUpdateInput(input: (InputDef, InputControl[?])*) = input.foreach(in => controls.get(in._2.name).foreach(c => inputs.update(in._1, c.name)))
  def updateInput(input: (InputDef, InputControl[?])*) = input.foreach(in => controls.get(in._2.name).foreach(c => if (c.editable) inputs.update(in._1, c.name)))
  private[this] def loadInputMap(input: Seq[(InputDef, String)]) = inputs.addAll(inputs)

  def hasInput(in: InputDef) = inputs.get(in).isDefined
  def hasControl(ctrl: String) = controls.get(ctrl).isDefined

  def attach(scene: BABYLON.Scene) =  {
    val ptrObs = scene.onPointerObservable.add(mouseEvent)
    val keyObs = scene.onKeyboardObservable.add(keyboardEvent)
    detachfn = Some(() => { scene.onPointerObservable.remove(ptrObs); scene.onKeyboardObservable.remove(keyObs) })
  }
  def detach() = { detachfn.foreach(_()); detachfn = None }

  def find(input: InputDef) = inputs.get(input).flatMap(controls.get)

  // Stop browser events with preventDefault() (see https://doc.babylonjs.com/features/featuresDeepDive/cameras/customizingCameraInputs)
  val mouseEvent = (pInfo: BABYLON.PointerInfo, evState: BABYLON.EventState) => if (tooltipMgr.map(_.handleUi(pInfo)).getOrElse(true)) {
    if (pInfo.event.movementX!=0 || pInfo.event.movementY!=0) find(MousePositionInputDef.from(pInfo.event)).foreach(c => c.start(c.convertor.convert(pInfo)))
    if (pInfo.`type` == BABYLON_IMPL.PointerEventTypes.POINTERDOWN) find(MouseButtonInputDef.from(pInfo.event)).foreach(c => c.start(c.convertor.convert(pInfo)))
    else if (pInfo.`type` == BABYLON_IMPL.PointerEventTypes.POINTERUP) find(MouseButtonInputDef.from(pInfo.event)).foreach(c => c.end(c.convertor.convert(pInfo)))
    else if (pInfo.`type` == BABYLON_IMPL.PointerEventTypes.POINTERWHEEL) find(MouseWheelInputDef.from(pInfo.event)).foreach(c => c.start(c.convertor.convert(pInfo)))
    pInfo.event.preventDefault()
  }

  val keyboardEvent = (kInfo: BABYLON.KeyboardInfo, evState: BABYLON.EventState) => {
    if (kInfo.`type` == BABYLON_IMPL.KeyboardEventTypes.KEYDOWN) find(KeyboardInputDef.from(kInfo.event)).foreach(c => c.start(c.convertor.convert(kInfo)))
    else if (kInfo.`type` == BABYLON_IMPL.KeyboardEventTypes.KEYUP) find(KeyboardInputDef.from(kInfo.event)).foreach(c => c.end(c.convertor.convert(kInfo)))
    kInfo.event.preventDefault()
  }

  def controlInputs() = inputs.groupBy(_._2).view.mapValues(_.keys.toSet).flatMap((k, v) => controls.get(k) match {
    case Some(c) => Some(c, v)
    case None => None
  }).toMap
  def editableControlInputs() = inputs.groupBy(_._2).view.mapValues(_.keys.toSet).flatMap((k, v) => controls.get(k) match {
    case Some(c) if c.editable => Some(c, v)
    case _ => None
  }).toMap
  
  def getControl(name: String) = controls.get(name)
  def unreachableControls() = {
    val used = inputs.values.toSet
    controls.keySet.filter(!used.contains(_)) 
  }
  def unconnectedInputs() = inputs.filter(in => !hasControl(in._2)).map(_._1)
}

object ControlManager {
  lazy val empty = ControlManager()
  def emptyFor(scene: BABYLON.Scene) = ControlManager().tap(_.attach(scene))
}
