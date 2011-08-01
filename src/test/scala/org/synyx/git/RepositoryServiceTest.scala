package org.synyx.git

import org.scalatest.FunSuite
import java.io.File
import org.scalatest.junit.JUnitRunner
import org.junit.runner.RunWith

@RunWith(classOf[JUnitRunner])
class RepositoryServiceTest extends FunSuite {

  val service = RepositoryService

  test("repository is created") {
    val repo = new Repository(new File("/tmp/" + System.currentTimeMillis()), "git://github.com/fhopf/maven-deployment-from-webapp.git");
    
    service.updateRepo(repo)

    assert(repo.folder.exists)
    val gitDir = new File(repo.folder, ".git")
    assert(gitDir.exists)

  }

}
