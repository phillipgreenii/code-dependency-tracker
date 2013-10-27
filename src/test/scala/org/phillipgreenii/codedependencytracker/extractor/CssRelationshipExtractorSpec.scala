package org.phillipgreenii.codedependencytracker.extractor

import org.scalatest.{BeforeAndAfter, FunSpec}
import org.scalatest.matchers.ShouldMatchers
import org.phillipgreenii.codedependencytracker.{Relationship, UriEntity, EntityReference, FileEntity}
import scala.io.Source

class CssRelationshipExtractorSpec extends FunSpec with BeforeAndAfter with ShouldMatchers {
  def buildFileEntity(name: String, path: String, content: String) = {
    new FileEntity(name, path, Source.fromString(content))
  }

  var extractor: CssRelationshipExtractor = _

  before {
    extractor = new CssRelationshipExtractor()
  }

  describe("CssRelationshipExtractor") {

    it("should return empty list for non css file") {

      val relationships = extractor(buildFileEntity("notcss.txt", "notcss.txt", "not css content"))

      relationships should have length 0
    }

    it("should return empty list for css file with no relationships") {
      val content =
        s""".myclass {
          color: white
        }"""

      val relationships = extractor(buildFileEntity("test.css", "test.css", content))

      relationships should have length 0
    }

    /*
     *  url()
     */

    it("should return url relationship with link defined with url without quotes") {
      val content =
        s"""div#header {
              background-image: url(images/header-background.jpg);
        }"""

      val relationships = extractor(buildFileEntity("test.css", "test.css", content))

      relationships should have length 1
      assertCssRelationship(relationships(0), "references", "test.css", "images/header-background.jpg")
    }

    it("should return url relationship with link defined with url with single quotes") {
      val content =
        s"""div#header {
              background-image: url('images/header-background.jpg');
        }"""

      val relationships = extractor(buildFileEntity("test.css", "test.css", content))

      relationships should have length 1
      assertCssRelationship(relationships(0), "references", "test.css", "images/header-background.jpg")
    }

    it("should return url relationship with link defined with url with double quotes") {
      val content =
        s"""div#header {
              background-image: url("images/header-background.jpg");
        }"""

      val relationships = extractor(buildFileEntity("test.css", "test.css", content))

      relationships should have length 1
      assertCssRelationship(relationships(0), "references", "test.css", "images/header-background.jpg")
    }

    it("should return multiple url relationships when multiple links defined on different lines") {
      val content =
        s"""div#header {
              background-image: url("images/header-background.jpg");
        }
        div#footer {
              background-image: url("images/footer-background.jpg");
        }"""

      val relationships = extractor(buildFileEntity("test.css", "test.css", content))

      relationships should have length 2
      assertCssRelationship(relationships(0), "references", "test.css", "images/header-background.jpg")
      assertCssRelationship(relationships(1), "references", "test.css", "images/footer-background.jpg")
    }

    it("should return multiple url relationships when multiple links defined on same line") {
      val content =
        s"""div#header {
              cursor:  url(foo.cur), url(http://www.example.com/bar.gif), auto;
        }"""

      val relationships = extractor(buildFileEntity("test.css", "test.css", content))

      relationships should have length 2

      assertCssRelationship(relationships(0), "references", "test.css", "foo.cur")
      assertCssRelationship(relationships(1), "references", "test.css", "http://www.example.com/bar.gif")
    }

    /*
     *  @import
     */

    it("should return import relationship with import defined with string with single quotes and no media query") {
      val content =
        s"""@import 'custom.css';"""

      val relationships = extractor(buildFileEntity("test.css", "test.css", content))

      relationships should have length 1
      assertCssRelationship(relationships(0), "imports", "test.css", "custom.css")
    }

    it("should return import relationship with import defined with string with single quotes and media query") {
      val content =
        s"""@import 'custom.css' print;"""

      val relationships = extractor(buildFileEntity("test.css", "test.css", content))

      relationships should have length 1
      assertCssRelationship(relationships(0), "imports", "test.css", "custom.css")
    }

    it("should return import relationship with import defined with string with double quotes and no media query") {
      val content =
        s"""@import "custom.css";"""

      val relationships = extractor(buildFileEntity("test.css", "test.css", content))

      relationships should have length 1
      assertCssRelationship(relationships(0), "imports", "test.css", "custom.css")
    }

    it("should return import relationship with import defined with string with double quotes and media query") {
      val content =
        s"""@import "custom.css" print;"""

      val relationships = extractor(buildFileEntity("test.css", "test.css", content))

      relationships should have length 1
      assertCssRelationship(relationships(0), "imports", "test.css", "custom.css")
    }

    it("should return import relationship with import defined with url and no media query") {
      val content =
        s"""@import url('custom.css');"""

      val relationships = extractor(buildFileEntity("test.css", "test.css", content))

      relationships should have length 1
      assertCssRelationship(relationships(0), "imports", "test.css", "custom.css")
    }

    it("should return import relationship with import defined with url and media query") {
      val content =
        s"""@import url('custom.css') print;"""

      val relationships = extractor(buildFileEntity("test.css", "test.css", content))

      relationships should have length 1
      assertCssRelationship(relationships(0), "imports", "test.css", "custom.css")
    }

    /*
     * helpers
     */

    def assertCssRelationship(relationship: Relationship[FileEntity, _], expectedRelationshipType: String, expectedSourceId: String, expectedTargetId: String) = {
      relationship.name shouldEqual expectedRelationshipType
      val source: EntityReference[FileEntity] = relationship.source.asInstanceOf[EntityReference[FileEntity]]
      source.id shouldEqual expectedSourceId
      val target: EntityReference[UriEntity] = relationship.target.asInstanceOf[EntityReference[UriEntity]]
      target.id shouldEqual expectedTargetId
    }
  }
}
