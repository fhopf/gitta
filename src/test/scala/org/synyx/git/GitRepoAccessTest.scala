package org.synyx.git

import org.scalatest.FunSuite
import java.io.File
import org.scalatest.junit.JUnitRunner
import org.junit.runner.RunWith
import org.scalatest.matchers.ShouldMatchers

@RunWith(classOf[JUnitRunner])
class GitRepoAccessTest extends FunSuite with ShouldMatchers {

  val service = new GitRepoAccess

  def setupRepo() = {
    val repo = new RepositoryConfig("test", new File("/tmp/" + System.currentTimeMillis()), "git://github.com/fhopf/maven-deployment-from-webapp.git");
    service.updateRepo(repo)
    repo
  }

  test("repository is created") {

    val repo = setupRepo

    repo.folder.exists should be(true)
    val gitDir = new File(repo.folder, ".git")
    gitDir.exists should be(true)

  }

  test("log returns a list of commits") {
    val repo = setupRepo

    val commits = service.log(repo)

    commits.isEmpty should be(false)

    for (commit <- commits) {
      println(commit)
    }

  }

  test("log single commit for existing commit") {
    val repo = setupRepo()
    val commit = service.log(repo, "762706406039595ddba0")
    commit.getShortMessage() should be("more debug output")
  }

  test("log single commit for nonexisting commit") {
    val repo = setupRepo()
    val commit = service.log(repo, "nonexisting")
    commit should be(null)
  }
}
