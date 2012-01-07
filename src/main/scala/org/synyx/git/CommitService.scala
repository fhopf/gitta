package org.synyx.git

import org.eclipse.jgit.revwalk.RevCommit

class CommitService(private val repoService: GitRepoAccess) {

  def refresh(repoConfig: RepositoryConfig): Iterable[Commit] = {

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
      revCommits.map(revCommit => new Commit(repoConfig.name, revCommit))
    } else {
      List()
    }


  }


}