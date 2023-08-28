package com.jarrahtechnology.kassite.tooltips

import facade.babylonjs.*
import facade.babylonjs.global.BABYLON as BABYLON_IMPL
import facade.babylonjsGui.BABYLON.*
import facade.babylonjsGui.global.BABYLON.GUI as GUI_IMPL
import scala.scalajs.js.UndefOr
import scala.scalajs.js.timers.*
import scala.collection.immutable.HashSet

object TooltipManager {
  val handleEvents = HashSet(BABYLON_IMPL.PointerEventTypes.POINTERMOVE, BABYLON_IMPL.PointerEventTypes.POINTERWHEEL)
  val waitMillis = 1500
}

class TooltipManager(val ui: GUI_IMPL.AdvancedDynamicTexture) {
  var currentTarget = Option.empty[Any]
  var currentTooltip = Option.empty[Tooltip]
  var timer = Option.empty[SetTimeoutHandle]
  val tooltips = collection.mutable.HashMap.empty[Any, Tooltip]

  def lastUiControl = ui._lastControlOver(0)
  def shouldContinue = !lastUiControl.isDefined
  def handleUi(pInfo: BABYLON.PointerInfo): Boolean = lastUiControl.map(t => handle(Some(t), pInfo)).getOrElse(true)

  // Returns true if should continue pointer processing
  def handle(target: Option[Any], pInfo: BABYLON.PointerInfo): Boolean = {
    def updateCurrent = currentTooltip.map(_.handle(target, pInfo)).getOrElse(true) 
    if (updateCurrent && TooltipManager.handleEvents.contains(pInfo.`type`)) {
      cancelTimer()
      tooltips.get(target).map(tt => {
        currentTarget = Some(target)
        startTimer(tt, pInfo)
        tt.isPointerBlocker
      }).getOrElse(shouldContinue)
    }  else shouldContinue
  }

  def cancelTimer() = { timer.foreach(clearTimeout); timer = Option.empty[SetTimeoutHandle] }
  def startTimer(tooltip: Tooltip, pInfo: BABYLON.PointerInfo) = timer = Some(setTimeout(TooltipManager.waitMillis)(tooltip.display(pInfo)))

  def addTooltip(target: Any, tooltip: Tooltip) = tooltips.addOne(target -> tooltip)
  def addTooltipRecurse(ctrl: GUI_IMPL.Container, tooltip: Tooltip): Unit = {
    addTooltip(ctrl, tooltip)
    ctrl.children.foreach(c => if (c.isInstanceOf[GUI_IMPL.Container]) addTooltipRecurse(c.asInstanceOf[GUI_IMPL.Container], tooltip))
  }
}

trait Tooltip {
  // returns true iff tooltip no longer needs to be displayed
  def handle(target: Option[Any], pInfo: BABYLON.PointerInfo) = true
  def display(pInfo: BABYLON.PointerInfo) = {}
  def isPointerBlocker: Boolean
}
