package org.phillipgreenii.codedependencytracker

import org.scalatest.{FeatureSpec, GivenWhenThen}
import org.scalatest.concurrent.Timeouts
import org.scalatest.time.SpanSugar._

class AppFunSpec extends FeatureSpec with GivenWhenThen with Timeouts {


  feature("App") {

    scenario("app should run") {
      Given("an app")
      When("the app runs")
      App.run()
      Then("no errors occur")
    }
  }

}
