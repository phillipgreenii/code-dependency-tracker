package org.phillipgreenii.codedependencytracker

import org.scalatest.{BeforeAndAfter, FunSpec}
import org.scalatest.matchers.ShouldMatchers

class AppSpec extends FunSpec with BeforeAndAfter with ShouldMatchers {

  describe("App") {

    it("should run") {
      App.run()
    }

  }

}