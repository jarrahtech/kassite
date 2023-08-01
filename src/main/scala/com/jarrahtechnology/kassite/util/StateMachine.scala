package com.jarrahtechnology.kassite

trait State {
  def onEnter(action: Transition): Unit = { }
  def onExit(action: Transition): Unit = { }
}

trait Transition {
  def onAction(from: State, to: State): Boolean = true
}

final class StateMachine[S <: State](val initial: S, val transitions: (S, Transition) => Option[S]) {
  private var curr = initial

  def transition(action: Transition): Option[S] = peek(action).flatMap(set(_, action))
  def peek(action: Transition): Option[S] = transitions(curr, action)
  def reset() = curr = initial
  def current() = curr
  private def set(next: S, action: Transition) = if (action.onAction(curr, next)) {
    curr.onExit(action)
    curr = next
    curr.onEnter(action)
    Some(curr)
  } else None
}

object StateMachine {

  class Builder[S <: State](val initialState: S) {
    private type TransitionFn = PartialFunction[(S, Transition), Option[S]]
    private var transitions: Seq[TransitionFn] = List.empty

    def transition(next: TransitionFn): Builder[S] = {
      transitions = transitions :+ next
      this
    }

    def build(): StateMachine[S] = {
      require(!transitions.isEmpty, ">=1 transition")
      StateMachine(initialState, (s, t) => transitions.find(_.isDefinedAt(s, t)).flatMap(_.apply(s, t)))
    }
  }
}