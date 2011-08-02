package org.synyx.git

import org.scalatest.FunSuite
import java.io.File
import org.scalatest.junit.JUnitRunner
import org.scalatest.matchers.ShouldMatchers
import org.junit.runner.RunWith

@RunWith(classOf[JUnitRunner])
class RepositoryServiceTest extends FunSuite with ShouldMatchers {

  val service = RepositoryService

  test("repository is created") {
    val repo = new Repository(new File("/tmp/" + System.currentTimeMillis()), "git://github.com/fhopf/maven-deployment-from-webapp.git");
    
    service.updateRepo(repo)

    repo.folder.exists should be(true)
    val gitDir = new File(repo.folder, ".git")
    gitDir.exists should be(true)

  }

}
