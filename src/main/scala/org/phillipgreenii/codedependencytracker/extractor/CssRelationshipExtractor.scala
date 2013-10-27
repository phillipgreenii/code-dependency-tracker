package org.phillipgreenii.codedependencytracker.extractor

import org.phillipgreenii.codedependencytracker._

class CssRelationshipExtractor extends EntityRelationshipExtractor[FileEntity] {

  val linkPattern = """url\(([^)]*)\)""".r;
  val importPattern = """\s*@import\s+(\S*).*;""".r;

  def apply(fileEntity: FileEntity): List[Relationship[FileEntity, _]] = {
    if (!fileEntity.name.endsWith(".css")) {
      List()
    }
    else {
      def generateRelationships(url: String, relationshipType: String): Relationship[FileEntity, UriEntity] = {
        new Relationship(fileEntity.asReference(), new EntityReference[UriEntity](url), relationshipType)
      }
      fileEntity.source.getLines().map(extractRelationships).flatten.map((generateRelationships _).tupled).toList
    }
  }

  private def extractRelationships(line: String): Seq[Pair[String, String]] = {
    line match {
      case importPattern(stringOrUrl) => extractImportRelationships(stringOrUrl)
      case _ => extractReferenceRelationships(line)
    }
  }

  private def extractImportRelationships(stringOrUrl: String) = {
    var url: String = stripQuotes((for (linkPattern(url) <- linkPattern findFirstMatchIn stringOrUrl) yield url).getOrElse(stringOrUrl))
    Seq(Pair(url, "imports"))
  }

  private def extractReferenceRelationships(line: String) = {
    getUrls(line).toStream.zip(Stream.continually("references"))
  }

  private def getUrls(text: String): Iterator[String] = for (linkPattern(url) <- linkPattern findAllIn text) yield stripQuotes(url)

  private def stripQuotes(text: String): String = {
    if (("\"\'" contains text(0)) && (text.take(1) == text.takeRight(1))) {
      text.substring(1, text.length - 1)
    } else {
      text
    }
  }
}
