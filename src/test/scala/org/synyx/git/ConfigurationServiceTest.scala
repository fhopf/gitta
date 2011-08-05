package org.synyx.git

import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import org.scalatest.matchers.ShouldMatchers
import org.junit.runner.RunWith


@RunWith(classOf[JUnitRunner])
class ConfigurationServiceTest extends FunSuite with ShouldMatchers {

  test("illegal lines are skipped") {
    val result = ConfigurationService.readRepositoryConfig("src/test/resources/repo.config")
    result.size should be(2)
  }

  test("legal lines are converted to Repository") {
    val result = ConfigurationService.readRepositoryConfig("src/test/resources/repo.config")
    for (item <- result) {
      assert(item.isInstanceOf[Repository])
    }
  }

  test("values are contained in right order") {
    val result = ConfigurationService.readRepositoryConfig("src/test/resources/repo.config").toList
    // first config line
    val repo1: Repository = result.lift(0).get
    repo1.name should be("name")
    repo1.folder.getAbsolutePath should be("/path")
    repo1.url should be("git://url")
    // last config line
    val repo2: Repository = result.lift(1).get
    repo2.name should be("anotherName")
    repo2.folder.getAbsolutePath should be("/some/path")
    repo2.url should be("https://url")
  }
}
