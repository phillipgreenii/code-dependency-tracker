package org.phillipgreenii.codedependencytracker

trait EntityRelationshipExtractor[E <: Entity] extends ((E) => List[Relationship[E, _]]) {

}
