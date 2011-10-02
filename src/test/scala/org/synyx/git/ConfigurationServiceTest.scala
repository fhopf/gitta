package org.synyx.git

import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import org.scalatest.matchers.ShouldMatchers
import org.junit.runner.{Result, RunWith}

@RunWith(classOf[JUnitRunner])
class ConfigurationServiceTest extends FunSuite with ShouldMatchers {

  val configService = new ConfigurationService

  test("illegal lines are skipped") {
    val result = configService.readRepositoryConfig("src/test/resources/repo.config")
    result.size should be(2)
  }

  test("legal lines are converted to RepositoryConfig") {
    val result = configService.readRepositoryConfig("src/test/resources/repo.config")
    for (item <- result) {
      assert(item.isInstanceOf[RepositoryConfig])
    }
  }

  test("values are contained in right order") {
    val result = configService.readRepositoryConfig("src/test/resources/repo.config").toList
    // first config line
    val repo1: RepositoryConfig = result.lift(0).get
    repo1.name should be("name")
    repo1.folder.getAbsolutePath should be("/path")
    repo1.url should be("git://url")
    // last config line
    val repo2: RepositoryConfig = result.lift(1).get
    repo2.name should be("anotherName")
    repo2.folder.getAbsolutePath should be("/some/path")
    repo2.url should be("https://url")
  }

  test("irc config object is IrcServer") {
    val result = configService.readIrcConfig("src/test/resources/irc.config")
    assert(result.isInstanceOf[IrcServer])
  }

  test("irc config has server set") {
    val result = configService.readIrcConfig("src/test/resources/irc.config")
    result.server should be("irc.synyx.de")
  }

  test("irc config has channels set") {
    val result = configService.readIrcConfig("src/test/resources/irc.config")
    result.channels.length should be(2)
    result.channels(0) should be("gitta")
    result.channels(1) should be("testchannel")
  }
}
