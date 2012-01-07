package org.synyx.git

import org.scalatest.FunSuite
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.mock.MockitoSugar
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

import org.mockito.Mockito._
import org.hamcrest.CoreMatchers._

import java.io.File
import org.eclipse.jgit.revwalk.RevCommit

@RunWith(classOf[JUnitRunner])
class CommitServiceTest extends FunSuite with ShouldMatchers with MockitoSugar {

  private val gitRepoMock = mock[GitRepoAccess]
  private val commitService = new CommitService(gitRepoMock)
  
  test("no changes on the repo returns no commits") {

    val repo1 = mock[RepositoryConfig]
    val lastCommit = mock[RevCommit]

    when(repo1.isCheckedOut()).thenReturn(true)
    when(gitRepoMock.latestCommit(repo1)).thenReturn(lastCommit)

    val result = commitService.refresh(List(repo1))
    result.size should be(0)
  }
  
  test("a single change on one repo that is not checked out") {

    val repo1 = mock[RepositoryConfig]
    val freshCommit = mock[RevCommit]
    
    when(repo1.isCheckedOut()).thenReturn(false)
    when(gitRepoMock.latestCommit(repo1)).thenReturn(freshCommit)
    when(gitRepoMock.logSince(repo1, null)).thenReturn(List(freshCommit))

    when(repo1.name).thenReturn("repoName")

    val transformedCommit = new Commit("repoName", freshCommit)

    val result = commitService.refresh(List(repo1))
    result.size should be(1)
    result.toList(0) should be(transformedCommit)

  }

}