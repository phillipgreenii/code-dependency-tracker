package org.phillipgreenii.codedependencytracker

import org.scalatest.{BeforeAndAfter, FunSpec}
import org.scalatest.matchers.ShouldMatchers
import scala.io.Source

class FileEntitySpec extends FunSpec with BeforeAndAfter with ShouldMatchers {
  var fileEntity: FileEntity = _

  before {
    fileEntity = new FileEntity("file.ext", "org/file.ext", Source.fromString("file content"))
  }

  describe("FileEntity") {

    describe("fromPath") {

      it("should construct FileEntity from path") {
        val path = getClass.getResource("testfile.txt").getPath
        val fileEntity: FileEntity = FileEntity.fromPath(path)

        assertResult("testfile.txt") {
          fileEntity.name
        }

        assertResult(path) {
          fileEntity.path
        }

        assertResult("content of test file") {
          fileEntity.source.mkString
        }

        assertResult(path) {
          fileEntity.id()
        }
      }

    }


    describe("constructor") {


      it("should construct") {


        assertResult("file.ext") {
          fileEntity.name
        }

        assertResult("org/file.ext") {
          fileEntity.path
        }

        assertResult("file content") {
          fileEntity.source.mkString
        }

        assertResult("org/file.ext") {
          fileEntity.id()
        }
      }
    }


    describe("asReference") {

      it("should return reference of correct type and correct id")   {
        val reference:EntityReference[FileEntity] = fileEntity.asReference()

        assertResult("org/file.ext") {
          reference.id
        }
      }
    }
  }
}
