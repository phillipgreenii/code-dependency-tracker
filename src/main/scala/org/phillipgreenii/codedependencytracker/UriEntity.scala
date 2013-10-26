package org.phillipgreenii.codedependencytracker

import java.net.URI

object UriEntity {
  def fromUriString(uriString: String): UriEntity = {
    new UriEntity(URI.create(uriString).normalize())
  }
}

class UriEntity(val uri: URI) extends Entity {
  type Self = UriEntity

  def id(): String = uri.toString
}
