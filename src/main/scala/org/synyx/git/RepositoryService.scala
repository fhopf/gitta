package org.synyx.git

import org.eclipse.jgit.api.{CloneCommand, LogCommand, Git}
import java.io.File
import org.eclipse.jgit.lib.{ObjectId, AnyObjectId, Repository, RepositoryBuilder}
import org.eclipse.jgit.revwalk.{RevWalk, RevCommit}

import scala.collection.JavaConversions._

/**
 * Functionality for talking to a local and remote git repo.
 */
class RepositoryService {

  def updateRepo(repo: RepositoryConfig): Unit = {
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
      case null => null
      case _ => {
        val walk = new RevWalk(jGitRepo)
        // TODO add the action for getting a List of commits
        null
      }
    }
  }


  // TODO use Option?
  def latestCommit(repo: RepositoryConfig): RevCommit = {
    log(repo) head	
  }

  def readGitDir(repo: RepositoryConfig): Git = {

    new Git(buildRepo(repo));
    
  }

  def buildRepo(repo: RepositoryConfig): Repository = {
    val builder = new RepositoryBuilder();
    builder.setWorkTree(repo.folder).build();
  }
}
