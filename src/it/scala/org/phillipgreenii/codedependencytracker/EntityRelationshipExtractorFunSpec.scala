package org.phillipgreenii.codedependencytracker

import org.scalatest.{GivenWhenThen, FeatureSpec}
import scala.io.Source

class EntityRelationshipExtractorFunSpec extends FeatureSpec with GivenWhenThen {
  feature("EntityRelationshipExtractor") {

    scenario("EntityRelationshipExtractor should extract relationships") {
      Given("an EntityRelationshipExtractor")
      val entityRelationshipExtractor: DummyEntityRelationshipExtractor = new DummyEntityRelationshipExtractor()
      And("Entity")
      //TODO: fix me; use FileEntity.fromPath()
      val entity: FileEntity = new FileEntity("file.txt","madeup/path/file.txt",Source.fromString("url/path/to/file.ext"))
      When("extraction occurs")
      val relationships: List[Relationship[FileEntity, _]] = entityRelationshipExtractor(entity)
      Then("correct relationships are generated")
      assertResult("madeup/path/file.txt") {
        relationships(0).source.id
      }
      assertResult("url/path/to/file.ext") {
        relationships(0).target.id
      }
    }
  }
}
