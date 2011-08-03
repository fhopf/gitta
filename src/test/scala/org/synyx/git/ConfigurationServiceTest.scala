package org.synyx.git

import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import org.scalatest.matchers.ShouldMatchers
import org.junit.runner.RunWith

@RunWith(classOf[JUnitRunner])
class ConfigurationServiceTest extends FunSuite with ShouldMatchers {

  test("illegal lines are skipped") {
    import scala.collection.JavaConversions._
    val result = ConfigurationService.readRepositoryConfig("src/test/resources/repo.config")
    result.size should be(2)
  }

}
