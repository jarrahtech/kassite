package com.jarrahtechnology.kassite.tween

import scala.scalajs.js.annotation.*
import scala.concurrent.duration.*
import com.jarrahtechnology.util.Math.*

type ProgressFn = Duration => Duration => Double
type FinishedFn = Duration => Duration => Boolean

@JSExportAll
class LoopType(val progress: ProgressFn, val hasFinished: FinishedFn)

@JSExportAll
object LoopType {

  def runForever: FinishedFn = _ => _ => false
  def stopAfterDuration: FinishedFn = duration => runTime => runTime>=duration
  def stopAfterRepeating(times: Int): FinishedFn = duration => runTime => runTime.toNanos/duration.toNanos>=times

  def forward: ProgressFn = duration => runTime => clamp01(runTime/duration)
  def reverse: ProgressFn = duration => runTime => clamp01(1- runTime/duration)
  def loopForwardFromStart: ProgressFn = duration => runTime => mod(runTime, duration)/duration
  def loopForwardThenReverse: ProgressFn = duration => runTime => {
      val runPercent = mod(runTime, duration)/duration
      if (isFloorEven(runTime/duration)) runPercent else 1-runPercent // backward
    }
  def repeat(times: Int, underlying: ProgressFn, end: Double): ProgressFn = 
    duration => runTime => if (stopAfterRepeating(times)(duration)(runTime)) end else underlying(duration)(runTime)

  case object Once extends LoopType(forward, stopAfterDuration)
  case object Reverse extends LoopType(reverse, stopAfterDuration)
  final case class Cycle(times: Int) extends LoopType(repeat(times, loopForwardFromStart, 1), stopAfterRepeating(times))
  case object CycleForever extends LoopType(loopForwardFromStart, runForever)
  final case class PingPong(times: Int) extends LoopType(repeat(times, loopForwardThenReverse, if (isEven(times)) 0d else 1d), stopAfterRepeating(times))
  case object PingPongForever extends LoopType(loopForwardThenReverse, runForever)
}
