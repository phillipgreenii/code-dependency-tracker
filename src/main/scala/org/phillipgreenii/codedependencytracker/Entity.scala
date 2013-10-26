package org.phillipgreenii.codedependencytracker


trait Entity {
  type Self <: Entity

  def id(): String

  def asReference():EntityReference[Self] = {
    new EntityReference[Self](id())
  }
}
