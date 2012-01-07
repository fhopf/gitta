package org.synyx.git

import org.eclipse.jgit.api.{CloneCommand, Git}
import java.io.File
import org.eclipse.jgit.revwalk.{RevWalk, RevCommit}

import scala.collection.JavaConversions._
import org.eclipse.jgit.lib._

/**
 * Functionality for talking to a local and remote git repo.
 */
class GitRepoAccess {

  def updateRepo(repo: RepositoryConfig) {
    val clone = new CloneCommand().setDirectory(repo.folder);
    val git = clone.setURI(repo.url).call();

    git.pull().call();

  }

  def log(repo: RepositoryConfig): Iterable[RevCommit] = {

    val git = readGitDir(repo)

    iterableAsScalaIterable(git.log().call());
  }

  def log(repo: RepositoryConfig, commit: String): RevCommit = {
    val jGitRepo = buildRepo(repo)

    val commitObject = jGitRepo.resolve(commit)
    if (commitObject != null) {
      val walk = new RevWalk(jGitRepo);
      walk.parseCommit(commitObject);
    } else {
      null
    }
  }

  // TODO this is exactly the same code as above 
  // refactor and pass in a function 
  def logSince(repo: RepositoryConfig, commit: RevCommit): Iterable[RevCommit] = {
    val jGitRepo = buildRepo(repo)

    commit match {
      // TODO this returns all commits, this is probably too much for a normal startup ;)
      case null => log(repo)
      case _ => {
        readGitDir(repo).log().not(commit).call()
      }
    }
  }


  // TODO this throws an exception for an empty repo
  def latestCommit(repo: RepositoryConfig): RevCommit = {
    log(repo) head
  }

  private def readGitDir(repo: RepositoryConfig): Git = {

    new Git(buildRepo(repo));

  }

  private def buildRepo(repo: RepositoryConfig): Repository = {
    val builder = new RepositoryBuilder();
    builder.setWorkTree(repo.folder).build();
  }
}
