package org.phillipgreenii.codedependencytracker

import org.scalatest.{BeforeAndAfter, FunSpec}
import org.scalatest.matchers.ShouldMatchers
import java.net.URI

class UriEntitySpec extends FunSpec with BeforeAndAfter with ShouldMatchers {
  var uriEntity: UriEntity = _

  before {
    uriEntity = new UriEntity(URI.create("path/to/file.ext"))
  }

  describe("UriEntity") {

    describe("fromPath") {

      it("should construct UriEntity from absolute uri") {
        val path = "http://google.com"
        val uriEntity: UriEntity = UriEntity.fromUriString(path)

        assertResult("http://google.com") {
          uriEntity.uri.toString
        }

        assertResult("http://google.com") {
          uriEntity.id()
        }
      }

      it("should construct UriEntity from relative uri") {
        val path = "path/to/file.ext"
        val uriEntity: UriEntity = UriEntity.fromUriString(path)

        assertResult("path/to/file.ext") {
          uriEntity.uri.toString
        }

        assertResult("path/to/file.ext") {
          uriEntity.id()
        }
      }
    }

    describe("constructor") {

      it("should construct") {

        assertResult("path/to/file.ext") {
          uriEntity.uri.toString
        }

        assertResult("path/to/file.ext") {
          uriEntity.id()
        }
      }
    }

    describe("asReference") {

      it("should return reference of correct type and correct id") {
        val reference: EntityReference[UriEntity] = uriEntity.asReference()

        assertResult("path/to/file.ext") {
          reference.id
        }
      }
    }
  }
}
