package org.synyx.git

import org.eclipse.jgit.api.{CloneCommand, LogCommand, Git}
import org.eclipse.jgit.lib.RepositoryBuilder

import java.io.File

/**
 * Functionality for talking to a local and remote git repo.
 */
object RepositoryService {

  def updateRepo() = {
    val clone = new CloneCommand();
    val git = clone.setURI("http://git.synyx.org/opencms-solr-module.git").call();

    git.pull().call();

  }

  def log() = {

    val builder = new RepositoryBuilder();
    val repository = builder.setGitDir(new File("/my/git/directory"))
    .readEnvironment() // scan environment GIT_* variables
    .findGitDir() // scan up the file system tree
    .build();

    val git = new Git(repository);

    git.log().call();
  }

}
