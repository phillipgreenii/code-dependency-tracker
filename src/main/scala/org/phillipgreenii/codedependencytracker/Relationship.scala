package org.phillipgreenii.codedependencytracker

import scala.collection.immutable.HashMap

class Relationship[S <: Entity, T <: Entity](val source: EntityReference[S], val target: EntityReference[T], val name: String, val properties: Map[String, String] = new HashMap[String, String]) {

}
