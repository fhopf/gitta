package org.synyx.git

import org.eclipse.jgit.api.{CloneCommand, LogCommand, Git}
import org.eclipse.jgit.lib.RepositoryBuilder

import java.io.File

/**
 * Functionality for talking to a local and remote git repo.
 */
object RepositoryService {

  def updateRepo(repo: Repository) = {
    val clone = new CloneCommand().setDirectory(repo.folder);
    val git = clone.setURI(repo.url).call();

    git.pull().call();

  }

  def log(repo: Repository) = {

    val builder = new RepositoryBuilder();
    val repository = builder.setGitDir(repo.folder)
    .readEnvironment() // scan environment GIT_* variables
    .findGitDir() // scan up the file system tree
    .build();

    val git = new Git(repository);

    git.log().call();
  }

}
