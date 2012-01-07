package org.synyx.git

import collection.mutable.ListBuffer
import org.eclipse.jgit.revwalk.RevCommit

class CommitService(private val repoService: GitRepoAccess) {

  def refresh(repoConfigs: Iterable[RepositoryConfig]): Iterable[Commit] = {

    var commits: Iterable[Commit] = new ListBuffer[Commit]

    for (repoConfig <- repoConfigs) {

      val theLatestCommit: RevCommit = if (!repoConfig.isCheckedOut()) {
        null
      } else {
        // get current commit
        repoService.latestCommit(repoConfig)
      }

      // update
      repoService.updateRepo(repoConfig)

      // log from last commit to now
      // only do something if there are new commits
      if (repoService.latestCommit(repoConfig) != theLatestCommit) {
        val revCommits: Iterable[RevCommit] = repoService.logSince(repoConfig, theLatestCommit)
        commits = commits ++ (revCommits.map(revCommit => new Commit(repoConfig.name, revCommit)))
      }
    }

    commits
  }


}