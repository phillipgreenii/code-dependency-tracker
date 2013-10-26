package org.phillipgreenii.codedependencytracker


class DummyEntityRelationshipExtractor extends EntityRelationshipExtractor[FileEntity] {
  def apply(fileEntity: FileEntity): List[Relationship[FileEntity, _]] = {
    List(new Relationship[FileEntity, UriEntity](fileEntity.asReference(), new EntityReference[UriEntity](fileEntity.source.mkString), "likes"))
  }
}
