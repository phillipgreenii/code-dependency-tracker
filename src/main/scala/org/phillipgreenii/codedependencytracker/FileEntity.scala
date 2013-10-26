package org.phillipgreenii.codedependencytracker

import java.io.File
import scala.io.Source

object FileEntity {
  def fromPath(filePath: String): FileEntity = {
    val file = new File(filePath)
    new FileEntity(file.getName, file.getCanonicalPath, Source.fromFile(file))
  }
}

class FileEntity(val name: String, val path: String, val source: Source) extends Entity {
  type Self = FileEntity

  def id(): String = path
}
