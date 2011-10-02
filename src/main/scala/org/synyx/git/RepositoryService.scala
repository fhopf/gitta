package org.synyx.git

import org.eclipse.jgit.api.{CloneCommand, LogCommand, Git}
import java.io.File
import org.eclipse.jgit.lib.{ObjectId, AnyObjectId, Repository, RepositoryBuilder}
import org.eclipse.jgit.revwalk.{RevWalk, RevCommit}

/**
 * Functionality for talking to a local and remote git repo.
 */
class RepositoryService {

  def updateRepo(repo: RepositoryConfig) = {
    val clone = new CloneCommand().setDirectory(repo.folder);
    val git = clone.setURI(repo.url).call();

    git.pull().call();

  }

  def log(repo: RepositoryConfig) = {

    val git = readGitDir(repo)
    
    git.log().call();
  }

  def log(repo: RepositoryConfig, commit: String) = {
    val jGitRepo = buildRepo(repo)

    val commitObject = jGitRepo.resolve(commit)
    if (commitObject != null) {
      val walk = new RevWalk(jGitRepo);
      walk.parseCommit(commitObject);
    } else {
      null
    }
  }

  def readGitDir(repo: RepositoryConfig) = {

    new Git(buildRepo(repo));
    
  }

  def buildRepo(repo: RepositoryConfig) = {
    val builder = new RepositoryBuilder();
    builder.setWorkTree(repo.folder).build();
  }
}
